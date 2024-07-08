package com.ims.insurance_management_system.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import com.ims.insurance_management_system.Exception.LoginException;
import com.ims.insurance_management_system.Exception.UserException;
import com.ims.insurance_management_system.Model.Admin;
import com.ims.insurance_management_system.Model.CurrentUserSession;
import com.ims.insurance_management_system.Model.LoginDTO;
import com.ims.insurance_management_system.Service.AdminService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("api/admin")
@Validated
public class AdminController {

    @Autowired
    private AdminService adminservice;

    @PostMapping("/")
    public ResponseEntity<Admin> adminRegistration(@Valid @RequestBody Admin admin) throws UserException {
        Admin newAdmin = adminservice.registerAdmin(admin);

        return new ResponseEntity<Admin>(newAdmin, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<CurrentUserSession> adminLogin(@Valid @RequestBody LoginDTO dto)
            throws UserException, LoginException {
        CurrentUserSession activeAdmin = adminservice.logIntoAccount(dto);

        return new ResponseEntity<CurrentUserSession>(activeAdmin, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> adminLogout(@RequestParam("key") String key) throws UserException, LoginException {
        String logOutAdmin = adminservice.logOutFromAccount(key);
        return new ResponseEntity<String>(logOutAdmin, HttpStatus.OK);
    }

}
