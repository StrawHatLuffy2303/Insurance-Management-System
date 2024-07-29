package com.ims.insurance_management_system.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "insurance_policies")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsurancePolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @Column(name = "policy_number", unique = true, columnDefinition = "VARCHAR(255) NOT NULL UNIQUE COMMENT 'Policy number must be unique'")
    private String policyNumber;

    @Column(name = "type")
    @NotBlank
    private String type;

    @Column(name = "coverage_amount")
    @NotNull
    private Long coverageAmount;

    @Min(value = 99)
    private Long premium;

    @NotNull
    @Column(name = "start_Date")
    private LocalDate startDate;

    @Future(message = "End date must greater then start date")
    @Column(name = "end_date")
    private LocalDate endDate;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.MERGE)
    private List<Client> clients = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "insurancePolicy", cascade = CascadeType.MERGE)
    private List<Claim> claims = new ArrayList<>();
}
