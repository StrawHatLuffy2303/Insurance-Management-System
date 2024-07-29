package com.ims.insurance_management_system.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ims.insurance_management_system.Exception.ClaimException;
import com.ims.insurance_management_system.Exception.LoginException;
import com.ims.insurance_management_system.Exception.PolicyException;
import com.ims.insurance_management_system.Exception.UserException;
import com.ims.insurance_management_system.Model.Claim;
import com.ims.insurance_management_system.Model.ClaimDTO;
import com.ims.insurance_management_system.Service.ClaimService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/claims")
public class ClaimController {

    @Autowired
    private ClaimService claimService;

    @GetMapping("/")
    public ResponseEntity<List<ClaimDTO>> getAllClaims(@RequestParam String key) throws UserException, LoginException {
        List<ClaimDTO> claims = claimService.getAllClaims(key);
        return new ResponseEntity<List<ClaimDTO>>(claims, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClaimDTO> getClaimByID(@PathVariable("id") Long id, @RequestParam String key)
            throws LoginException, ClaimException {
        ClaimDTO claim = claimService.getClaimById(id, key);
        return new ResponseEntity<ClaimDTO>(claim, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Claim> saveClaim(@RequestBody Claim claim, @RequestParam String key,
            @RequestParam String policyNumber) throws LoginException, UserException, ClaimException, PolicyException {
        Claim newClaim = claimService.saveClaim(claim, key, policyNumber);

        return new ResponseEntity<Claim>(newClaim, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Claim> updateClaim(@PathVariable("id") Long id, @RequestBody Claim claim,
            @RequestParam String key, @RequestParam String claimStatus)
            throws LoginException, UserException, ClaimException, PolicyException {
        Claim newClaim = claimService.updateClaim(id, key, claimStatus);

        return new ResponseEntity<Claim>(newClaim, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Claim> deleteClaim(@PathVariable("id") Long id, @RequestParam String key)
            throws LoginException, ClaimException, UserException {
        Claim delClaim = claimService.deleteClaim(id, key);

        return new ResponseEntity<Claim>(delClaim, HttpStatus.OK);
    }
}
