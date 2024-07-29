package com.ims.insurance_management_system.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ims.insurance_management_system.Model.Claim;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {

    @EntityGraph(attributePaths = "insurancePolicy")
    List<Claim> findByInsurancePolicyIdAndUserId(Long policyId, Long userId);

    @SuppressWarnings("null")
    @EntityGraph(attributePaths = "insurancePolicy")
    List<Claim> findAll();
}
