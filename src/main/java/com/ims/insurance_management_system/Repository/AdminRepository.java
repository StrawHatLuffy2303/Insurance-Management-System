package com.ims.insurance_management_system.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ims.insurance_management_system.Model.Admin;



@Repository
public interface AdminRepository extends JpaRepository<Admin,Long>{
        public Admin findByMobileNo(String mobileNo);
}
