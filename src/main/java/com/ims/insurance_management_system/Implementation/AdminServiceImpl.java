package com.ims.insurance_management_system.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ims.insurance_management_system.Exception.UserException;
import com.ims.insurance_management_system.Model.Admin;
import com.ims.insurance_management_system.Repository.AdminRepository;
import com.ims.insurance_management_system.Service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminrepo;

    @Override
    public Admin registerAdmin(Admin admin) throws UserException {
        Admin newAdmin = adminrepo.findByMobileNo(admin.getMobileNo());
        if (newAdmin == null) {
            return adminrepo.save(admin);
        } else {
            throw new UserException("You are already registered");
        }
    }

}
