package com.ims.insurance_management_system.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ims.insurance_management_system.Exception.LoginException;
import com.ims.insurance_management_system.Exception.UserException;
import com.ims.insurance_management_system.Model.Admin;
import com.ims.insurance_management_system.Model.CurrentUserSession;
import com.ims.insurance_management_system.Model.LoginDTO;
import com.ims.insurance_management_system.Service.AdminService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("api/admin")
public class AdminController {

    @Autowired
    private AdminService adminservice;

    @PostMapping("/")
    public ResponseEntity<Admin> adminRegistration(@RequestBody Admin admin) throws UserException {
        Admin newAdmin = adminservice.registerAdmin(admin);

        return new ResponseEntity<Admin>(newAdmin, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<CurrentUserSession> adminLogin(@RequestBody LoginDTO dto)
            throws UserException,LoginException {
        CurrentUserSession activeAdmin = adminservice.logIntoAccount(dto);

        return new ResponseEntity<CurrentUserSession>(activeAdmin, HttpStatus.OK);
    }

}
