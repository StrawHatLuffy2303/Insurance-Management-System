package com.ims.insurance_management_system.Model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clients")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z]*$", message = "First name should not Contain Number and Special Character")
    @Column(name = "firstname")
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z]*$", message = "Middle name should not Contain Number and Special Character")
    @Column(name = "middletname")
    private String middleName;

    @Pattern(regexp = "^[a-zA-Z]*$", message = "Last name should not Contain Number and Special Character")
    @Column(name = "lastname")
    private String lastName;

    @NotNull
    private LocalDate dob;

    @NotBlank
    @Column(name = "mobile")
    @Size(min = 10, max = 10, message = "Mobile number must have 10 digits")
    private String mobileNo;

    @NotBlank
    private String address;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,20}$", message = "Password should be alphanumeric and must contain 6-12 characters having at least one special character, one upper case, one lowercase, and one digit")
    private String password;

}
