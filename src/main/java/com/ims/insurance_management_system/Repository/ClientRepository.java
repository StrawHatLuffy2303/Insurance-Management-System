package com.ims.insurance_management_system.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ims.insurance_management_system.Model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByMobileNo(String mobileNo);
}
