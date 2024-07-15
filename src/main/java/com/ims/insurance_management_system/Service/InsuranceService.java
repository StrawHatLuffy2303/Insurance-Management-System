package com.ims.insurance_management_system.Service;

import java.util.List;

import com.ims.insurance_management_system.Exception.ClientException;
import com.ims.insurance_management_system.Exception.LoginException;
import com.ims.insurance_management_system.Exception.PolicyException;
import com.ims.insurance_management_system.Exception.UserException;
import com.ims.insurance_management_system.Model.InsurancePolicy;

public interface InsuranceService {
        public List<InsurancePolicy> getAllPolicies();

        public InsurancePolicy getPolicyById(String policyNumber) throws PolicyException;

        public InsurancePolicy savePolicy(String key, InsurancePolicy policy) throws LoginException, UserException;

        public InsurancePolicy updatePolicy(String policyId, InsurancePolicy policy, String key)
                        throws LoginException, UserException, PolicyException;

        public InsurancePolicy deletePolicy(Long id, String key) throws LoginException, UserException, PolicyException;

        public String buyInsurancePolicy(String policyNumber, String key)
                        throws LoginException, ClientException, PolicyException;

        // public String buyPolicyForClient(Long id, String policyNumber, String key)
        // throws LoginException, PolicyException, ClientException;

}
