package com.ims.insurance_management_system.Implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ims.insurance_management_system.Exception.ClientException;
import com.ims.insurance_management_system.Exception.LoginException;
import com.ims.insurance_management_system.Exception.PolicyException;
import com.ims.insurance_management_system.Exception.UserException;
import com.ims.insurance_management_system.Model.Client;
import com.ims.insurance_management_system.Model.CurrentUserSession;
import com.ims.insurance_management_system.Model.InsurancePolicy;
import com.ims.insurance_management_system.Repository.AdminRepository;
import com.ims.insurance_management_system.Repository.ClientRepository;
import com.ims.insurance_management_system.Repository.InsuranceRepository;
import com.ims.insurance_management_system.Repository.SessionRepository;
import com.ims.insurance_management_system.Service.InsuranceService;

import jakarta.transaction.Transactional;

@Service
public class InsuranceServiceImpl implements InsuranceService {

    @Autowired
    private ClientRepository clientRepo;
    @Autowired
    private SessionRepository sessionRepo;
    @Autowired
    private InsuranceRepository insuranceRepo;
    @Autowired
    private AdminRepository adminRepo;

    @Override
    public List<InsurancePolicy> getAllPolicies() {
        return insuranceRepo.findAll();

    }

    @Override
    public InsurancePolicy getPolicyById(String policyNumber) throws PolicyException {
        InsurancePolicy policy = insuranceRepo.findByPolicyNumber(policyNumber);
        if (policy == null) {
            throw new PolicyException("Policy do not exists");
        }
        return policy;
    }

    @Override
    public InsurancePolicy savePolicy(String key, InsurancePolicy policy) throws LoginException, UserException {
        CurrentUserSession activeSession = sessionRepo.findByUuid(key);
        if (activeSession == null) {
            throw new LoginException("PLease Login first");
        }

        adminRepo.findById(activeSession.getCurrentUserID())
                .orElseThrow(() -> new UserException("Only admin can add new policy"));

        return insuranceRepo.save(policy);
    }

    @Override
    @Transactional(rollbackOn = { PolicyException.class })
    public InsurancePolicy updatePolicy(String policyId, InsurancePolicy policy, String key)
            throws LoginException, UserException, PolicyException {
        CurrentUserSession activeSession = sessionRepo.findByUuid(key);
        if (activeSession == null) {
            throw new LoginException("Please login to update policy");
        }

        if (!adminRepo.findById(activeSession.getCurrentUserID()).isPresent()
                && !activeSession.getRole().equals("ADMIN")) {
            throw new UserException("Only admin can update the policy");
        }

        if (insuranceRepo.findByPolicyNumber(policyId) != null) {
            InsurancePolicy newUpdatedPolicy = insuranceRepo.findByPolicyNumber(policyId);
            newUpdatedPolicy.setPolicyNumber(policy.getPolicyNumber());
            newUpdatedPolicy.setType(policy.getType());
            newUpdatedPolicy.setCoverageAmount(policy.getCoverageAmount());
            newUpdatedPolicy.setPremium(policy.getPremium());
            newUpdatedPolicy.setStartDate(policy.getStartDate());
            newUpdatedPolicy.setEndDate(policy.getEndDate());
            return insuranceRepo.save(newUpdatedPolicy);
        } else {
            throw new PolicyException("Invalid policy id");
        }

    }

    @Override
    @Transactional
    public InsurancePolicy deletePolicy(Long id, String key) throws LoginException, UserException, PolicyException {
        CurrentUserSession activeSession = sessionRepo.findByUuid(key);
        if (activeSession == null) {
            throw new LoginException("Please login to delete policy");
        }

        if (!adminRepo.findById(activeSession.getCurrentUserID()).isPresent()
                && !activeSession.getRole().equals("ADMIN")) {
            throw new UserException("Only admin can delete the policy");
        } else {
            InsurancePolicy policy = insuranceRepo.findById(id)
                    .orElseThrow(() -> new PolicyException("Policy not found"));
            for (Client client : policy.getClients()) {
                client.getInsurancePolicies().remove(policy);
            }

            insuranceRepo.deleteById(id);
            return policy;
        }

    }

    @Override
    @Transactional
    public String buyInsurancePolicy(String policyNumber, String key)
            throws LoginException, ClientException, PolicyException {
        CurrentUserSession activeSession = sessionRepo.findByUuid(key);
        if (activeSession == null) {
            throw new LoginException("Please login to buy policy. Session is invalid.");
        }

        InsurancePolicy policy = insuranceRepo.findByPolicyNumber(policyNumber);
        if (policy == null) {
            throw new PolicyException("Policy with number " + policyNumber + " does not exist.");
        }

        Client client = clientRepo.findById(activeSession.getCurrentUserID())
                .orElseThrow(() -> new ClientException("Please login first. Client not found."));
        if (client.getInsurancePolicies().contains(policy)) {
            throw new ClientException("Client already has the policy with number : " + policyNumber);
        }

        client.getInsurancePolicies().add(policy);
        policy.getClients().add(client);

        clientRepo.save(client);
        insuranceRepo.save(policy);
        return "You have successfully purchased " + policy.getType() + " policy with number " + policyNumber;
    }

    // if admin want to buy policy for client ......(we can add this if required)
    // @Transactional
    // public String buyPolicyForClient(Long id, String policyNumber, String key)
    // throws LoginException, PolicyException, ClientException {
    // // Check if current user is an admin
    // CurrentUserSession activeSession = sessionRepo.findByUuid(key);
    // if (activeSession.getRole().equals("ADMIN")) {
    // throw new LoginException("Only admins can buy policies for clients.");
    // }

    // Client client = clientRepo.findById(id).orElseThrow(() -> new
    // ClientException("Client not found."));
    // InsurancePolicy policy = insuranceRepo.findByPolicyNumber(policyNumber);
    // if (policy == null) {
    // throw new PolicyException("Policy with number " + policyNumber + " does not
    // exist.");
    // }

    // if (client.getInsurancePolicies().contains(policy)) {
    // throw new ClientException("Client already has the policy with number : " +
    // policyNumber);
    // }

    // client.getInsurancePolicies().add(policy);
    // policy.getClients().add(client);

    // clientRepo.save(client);
    // insuranceRepo.save(policy);
    // return "You have successfully purchased " + policy.getType() + " policy with
    // number " + policyNumber + " for client " + client.getFirstName();
    // }

}
