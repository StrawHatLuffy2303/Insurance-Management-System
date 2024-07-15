package com.ims.insurance_management_system.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.ims.insurance_management_system.Exception.ClientException;
import com.ims.insurance_management_system.Exception.LoginException;
import com.ims.insurance_management_system.Exception.PolicyException;
import com.ims.insurance_management_system.Exception.UserException;
import com.ims.insurance_management_system.Model.InsurancePolicy;
import com.ims.insurance_management_system.Service.InsuranceService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/policies")
public class InsuranceController {

    @Autowired
    private InsuranceService policyService;

    @GetMapping("/")
    public ResponseEntity<List<InsurancePolicy>> getAllPolicy() {
        List<InsurancePolicy> allpolicy = policyService.getAllPolicies();
        return new ResponseEntity<List<InsurancePolicy>>(allpolicy, HttpStatus.OK);
    }

    @GetMapping("/{num}")
    public ResponseEntity<InsurancePolicy> getPolicyById(@PathVariable("num") String policyNumber)
            throws PolicyException {
        InsurancePolicy policy = policyService.getPolicyById(policyNumber);
        return new ResponseEntity<InsurancePolicy>(policy, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<InsurancePolicy> savePolicy(@RequestBody InsurancePolicy policy, @RequestParam String key)
            throws LoginException, UserException {
        InsurancePolicy savePolicy = policyService.savePolicy(key, policy);

        return new ResponseEntity<InsurancePolicy>(savePolicy, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InsurancePolicy> updatePolicy(@PathVariable("id") String id,
            @RequestBody InsurancePolicy policy, @RequestParam String key)
            throws LoginException, UserException, PolicyException {
        InsurancePolicy updatePolicy = policyService.updatePolicy(id, policy, key);

        return new ResponseEntity<InsurancePolicy>(updatePolicy, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<InsurancePolicy> deletePolicy(@PathVariable("id") Long id, @RequestParam String key)
            throws LoginException, UserException, PolicyException {
        InsurancePolicy deletePolicy = policyService.deletePolicy(id, key);
        return new ResponseEntity<InsurancePolicy>(deletePolicy, HttpStatus.OK);
    }

    @PostMapping("/buyPolicy/{num}")
    public ResponseEntity<String> buyPolicy(@PathVariable("num") String policynum, @RequestParam String key)
            throws LoginException, ClientException, PolicyException {
        String buyPolicy = policyService.buyInsurancePolicy(policynum, key);

        return new ResponseEntity<String>(buyPolicy, HttpStatus.OK);
    }

    // @PostMapping("/buyForClient/{id}")
    // public ResponseEntity<String> buyPolicyForClient(@PathVariable("id") Long id, @RequestParam String policyNumber, @RequestParam String key) throws LoginException, PolicyException, ClientException {
    //     String buyByadmin= policyService.buyPolicyForClient(id, policyNumber, key);
    
    //     return new ResponseEntity<String>(buyByadmin, HttpStatus.OK);
    // }   

}
