package com.ims.insurance_management_system.Implementation;

import java.time.LocalDateTime;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ims.insurance_management_system.Exception.LoginException;
import com.ims.insurance_management_system.Exception.UserException;
import com.ims.insurance_management_system.Model.Admin;
import com.ims.insurance_management_system.Model.CurrentUserSession;
import com.ims.insurance_management_system.Model.LoginDTO;
import com.ims.insurance_management_system.Repository.AdminRepository;
import com.ims.insurance_management_system.Repository.SessionRepository;
import com.ims.insurance_management_system.Service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminrepo;

    @Autowired
    private SessionRepository sessionrepo;

    @Override
    public Admin registerAdmin(Admin admin) throws UserException {
        Admin newAdmin = adminrepo.findByMobileNo(admin.getMobileNo());
        if (newAdmin == null) {
            return adminrepo.save(admin);
        } else {
            throw new UserException("You are already registered");
        }
    }

    @Override
    public CurrentUserSession logIntoAccount(LoginDTO dto) throws UserException, LoginException {
        Admin existingAdmin = adminrepo.findByMobileNo(dto.getMobileNo());
        if (existingAdmin == null) {
            throw new UserException("Mobile number not found in our records");
        }

        if (!dto.getPassword().equals(existingAdmin.getPassword())) {
            throw new LoginException("Wrong Password");
        }

        if (sessionrepo.existsById(existingAdmin.getAdminid())) {
            throw new UserException("You have already logged in");
        }

        String uniqueKey = UUID.randomUUID().toString().replace("-", "").substring(0, 7);
        CurrentUserSession currentUser = new CurrentUserSession(existingAdmin.getAdminid(), "ADMIN", uniqueKey,
                LocalDateTime.now());
        sessionrepo.save(currentUser);

        return currentUser;

    }

    @Override
    public String logOutFromAccount(String key) throws UserException, LoginException {
        CurrentUserSession currentUserSession = sessionrepo.findByUuid(key);
        if (currentUserSession == null) {
            throw new LoginException("No Active Session Found");
        } else {
            sessionrepo.delete(currentUserSession);
        }
        return "Logout SuccessFully";
    }

}
