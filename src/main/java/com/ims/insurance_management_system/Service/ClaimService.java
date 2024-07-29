package com.ims.insurance_management_system.Service;

import java.util.List;

import com.ims.insurance_management_system.Exception.ClaimException;
import com.ims.insurance_management_system.Exception.LoginException;
import com.ims.insurance_management_system.Exception.PolicyException;
import com.ims.insurance_management_system.Exception.UserException;
import com.ims.insurance_management_system.Model.Claim;
import com.ims.insurance_management_system.Model.ClaimDTO;

public interface ClaimService {
    public List<ClaimDTO> getAllClaims(String key) throws UserException, LoginException;

    public ClaimDTO getClaimById(Long id, String key) throws LoginException, ClaimException;

    public Claim saveClaim(Claim claim, String key, String policyNumber)
            throws LoginException, UserException, ClaimException, PolicyException;

    public Claim updateClaim(Long id, String key, String claimStatus)
            throws LoginException, UserException, ClaimException, PolicyException;

    public Claim deleteClaim(Long id, String key) throws LoginException, ClaimException, UserException;
}
