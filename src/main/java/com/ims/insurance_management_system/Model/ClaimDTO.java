package com.ims.insurance_management_system.Model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClaimDTO {

    private Long id;
    private Long userId;
    private String description;
    private Long amount;
    private LocalDate claimDate;
    private ClaimStatus claimStatus;
    private InsurancePolicy insurancePolicy;
}
