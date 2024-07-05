package com.ims.insurance_management_system.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
     private String name;
     private String password;
     
     @JsonIgnore
     private String role;
}
