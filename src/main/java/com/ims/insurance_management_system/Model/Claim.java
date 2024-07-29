package com.ims.insurance_management_system.Model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "claims")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Claim {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @JsonIgnore
    private Long userId;

    @NotNull
    private String description;

    @NotNull
    @Column(name = "claim_amount")
    private Long amount;

    @NotNull
    @JsonIgnore
    @Column(name = "claim_date")
    private LocalDate claimDate;

    @JsonIgnore
    @Column(name = "claim_status")
    @Enumerated(EnumType.STRING)
    private ClaimStatus claimStatus;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "policy_id")
    private InsurancePolicy insurancePolicy;

}
