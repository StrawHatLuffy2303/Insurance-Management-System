package com.ims.insurance_management_system.Implementation;

import java.time.LocalDate;
// import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

//import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ims.insurance_management_system.Exception.ClaimException;
import com.ims.insurance_management_system.Exception.LoginException;
import com.ims.insurance_management_system.Exception.PolicyException;
import com.ims.insurance_management_system.Exception.UserException;
import com.ims.insurance_management_system.Model.Admin;
import com.ims.insurance_management_system.Model.Claim;
import com.ims.insurance_management_system.Model.ClaimDTO;
import com.ims.insurance_management_system.Model.ClaimStatus;
import com.ims.insurance_management_system.Model.Client;
import com.ims.insurance_management_system.Model.CurrentUserSession;
import com.ims.insurance_management_system.Model.InsurancePolicy;
import com.ims.insurance_management_system.Repository.AdminRepository;
import com.ims.insurance_management_system.Repository.ClaimRepository;
import com.ims.insurance_management_system.Repository.ClientRepository;
import com.ims.insurance_management_system.Repository.InsuranceRepository;
import com.ims.insurance_management_system.Repository.SessionRepository;
import com.ims.insurance_management_system.Service.ClaimService;

import jakarta.transaction.Transactional;

@Service
public class ClaimServiceImpl implements ClaimService {

    @Autowired
    private SessionRepository sessionRepo;

    @Autowired
    private AdminRepository adminRepo;

    @Autowired
    private InsuranceRepository insuranceRepo;

    @Autowired
    private ClientRepository clientRepo;

    @Autowired
    private ClaimRepository claimRepo;

    // using basic foreach loop and not using lambda expression and stream api...
    // @Override
    // public List<ClaimDto> getAllClaims(String key) throws UserException,
    // LoginException {
    // CurrentUserSession activeSession = sessionRepo.findByUuid(key);
    // if (activeSession == null) {
    // throw new UserException("Please login to see all claims");
    // }

    // Admin admin =
    // adminRepo.findById(activeSession.getCurrentUserId()).orElse(null);
    // if (admin == null) {
    // throw new LoginException("Admin Access Only");
    // }

    // List<Claim> claims = claimRepo.findAllClaims();
    // List<ClaimDto> claimDtos = new ArrayList<>();

    // ModelMapper modelMapper = new ModelMapper();
    // for (Claim claim : claims) {
    // ClaimDto claimDto = modelMapper.map(claim, ClaimDto.class);
    // claimDtos.add(claimDto);
    // }

    // return claimDtos;
    // }

    @Override
    public List<ClaimDTO> getAllClaims(String key) throws UserException, LoginException {
        CurrentUserSession activeSession = sessionRepo.findByUuid(key);
        if (activeSession == null) {
            throw new LoginException("Please login to see all claims");
        }
        Admin admin = adminRepo.findById(activeSession.getCurrentUserID()).orElse(null);
        if (admin == null) {
            throw new LoginException("Admin Access Only");
        }

        List<Claim> claims = claimRepo.findAll();
        return claims.stream().map(claim -> mapClaimToClaimDto(claim)).collect(Collectors.toList());

    }

    @Override
    public ClaimDTO getClaimById(Long id, String key) throws LoginException, ClaimException {
        CurrentUserSession activeSession = sessionRepo.findByUuid(key);
        if (activeSession == null) {
            throw new LoginException("Please login to access");
        }
        Claim claim = claimRepo.findById(id).orElse(null);
        if (claim == null) {
            throw new ClaimException("Invalid claim ID");
        }

        return mapClaimToClaimDto(claim);
    }

    @Override
    @Transactional
    public Claim saveClaim(Claim claim, String key, String policyNumber)
            throws LoginException, UserException, ClaimException, PolicyException {
        CurrentUserSession activeSession = sessionRepo.findByUuid(key);
        if (activeSession == null) {
            throw new LoginException("Please login first");
        }

        InsurancePolicy policy = insuranceRepo.findByPolicyNumber(policyNumber);
        if (policy == null) {
            throw new PolicyException("Invalid policy Number");
        }

        Client existingClient = clientRepo.findById(activeSession.getCurrentUserID()).orElse(null);
        if (!existingClient.getInsurancePolicies().contains(policy)) {
            throw new ClaimException("User don't have this policy");
        }

        List<Claim> existingClaimsForPolicyAndUser = claimRepo.findByInsurancePolicyIdAndUserId(policy.getId(),
                activeSession.getCurrentUserID());
        if (existingClaimsForPolicyAndUser != null && !existingClaimsForPolicyAndUser.isEmpty()) {
            throw new ClaimException("A claim already exists for this policy and user");
        }

        claim.setUserId(activeSession.getCurrentUserID());
        claim.setClaimDate(LocalDate.now());
        claim.setClaimStatus(ClaimStatus.PENDING);
        claim.setInsurancePolicy(policy);

        return claimRepo.save(claim);

    }

    @Override
    @Transactional
    public Claim updateClaim(Long id, String key, String claimStatus)
            throws LoginException, UserException, ClaimException, PolicyException {
        CurrentUserSession activeSession = sessionRepo.findByUuid(key);
        if (activeSession == null) {
            throw new LoginException("Please login to update claim");
        }
        Claim claim = claimRepo.findById(id)
                .orElseThrow(() -> new ClaimException("You have not applied claim with this claim id : " + id));

        Admin validAdmin = adminRepo.findById(activeSession.getCurrentUserID()).orElse(null);
        if (validAdmin != null && activeSession.getRole().equals("ADMIN")) {
            if (claimStatus == null) {
                throw new ClaimException("Claim status cannot be null");
            }
            try {
                ClaimStatus status = ClaimStatus.valueOf(claimStatus.toUpperCase());
                if (claim.getClaimStatus().equals(status)) {
                    // no need to update if the status is the same
                    return claim;
                }
                claim.setClaimStatus(status);
            } catch (IllegalArgumentException e) {
                throw new ClaimException(
                        "Invalid claim status value: Please choose from (OPEN/CLOSED/REJECTED/PENDING/ACCEPTED) ");
            }

            return claimRepo.save(claim);
        } else {
            throw new UserException("Only admin is allowed to update the claim");
        }
    }

    @Override
    @Transactional
    public Claim deleteClaim(Long id, String key) throws LoginException, ClaimException, UserException {
        CurrentUserSession activeSession = sessionRepo.findByUuid(key);
        if (activeSession == null) {
            throw new LoginException("Please login to delete claim");
        }

        adminRepo.findById(activeSession.getCurrentUserID())
                .orElseThrow(() -> new UserException("Admin not found"));

        if (!activeSession.getRole().equals("ADMIN")) {
            throw new UserException("Only admin with the specified role is allowed to Delete the claim");
        }

        Claim claim = claimRepo.findById(id).orElseThrow(() -> new ClaimException("Claim not found"));

        claimRepo.delete(claim);
        return claim;

    }

    // Map claims to ClaimDTO using a mapping function (explicit mapping approach)
    private ClaimDTO mapClaimToClaimDto(Claim claim) {
        ClaimDTO claimDto = new ClaimDTO();
        claimDto.setId(claim.getId());
        claimDto.setUserId(claim.getUserId());
        claimDto.setDescription(claim.getDescription());
        claimDto.setAmount(claim.getAmount());
        claimDto.setClaimDate(claim.getClaimDate());
        claimDto.setClaimStatus(claim.getClaimStatus());
        claimDto.setInsurancePolicy(claim.getInsurancePolicy());
        return claimDto;
    }

}
