package com.ims.insurance_management_system.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ims.insurance_management_system.Model.InsurancePolicy;

@Repository
public interface InsuranceRepository extends JpaRepository<InsurancePolicy, Long> {
    InsurancePolicy findByPolicyNumber(String policyNumber);

}
