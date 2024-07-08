package com.ims.insurance_management_system.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ims.insurance_management_system.Exception.LoginException;
import com.ims.insurance_management_system.Exception.UserException;
import com.ims.insurance_management_system.Model.Client;
import com.ims.insurance_management_system.Model.CurrentUserSession;
import com.ims.insurance_management_system.Model.LoginDTO;
import com.ims.insurance_management_system.Service.ClientService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/")
    public ResponseEntity<Client> registerClient(@RequestBody Client client) throws UserException, LoginException {
        Client newClient = clientService.saveClient(client);

        return new ResponseEntity<Client>(newClient, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<CurrentUserSession> loginClient(@RequestBody LoginDTO dto) throws UserException, LoginException {
        CurrentUserSession activeClient = clientService.logIntoAccount(dto);

        return new ResponseEntity<CurrentUserSession>(activeClient, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutClient(@RequestParam("key") String key) throws UserException, LoginException {
        String activeClient=clientService.logOutFromAccount(key);
        
        return new ResponseEntity<String>(activeClient,HttpStatus.OK);
    }
    

}
