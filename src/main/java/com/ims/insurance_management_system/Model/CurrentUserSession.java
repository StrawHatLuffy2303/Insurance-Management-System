package com.ims.insurance_management_system.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentUserSession {

    @Id
    @Column(unique = true)
    private Long CurrentUserID;
    private String role;
    private String uuid;
    private LocalDateTime localDateTime;
}
