package com.ims.insurance_management_system.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ims.insurance_management_system.Exception.ClientException;
import com.ims.insurance_management_system.Exception.LoginException;
import com.ims.insurance_management_system.Exception.UserException;
import com.ims.insurance_management_system.Model.Client;
import com.ims.insurance_management_system.Model.CurrentUserSession;
import com.ims.insurance_management_system.Model.LoginDTO;
import com.ims.insurance_management_system.Service.ClientService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/")
    public ResponseEntity<Client> registerClient(@RequestBody Client client)
            throws UserException, LoginException, ClientException {
        Client newClient = clientService.saveClient(client);

        return new ResponseEntity<Client>(newClient, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<CurrentUserSession> loginClient(@RequestBody LoginDTO dto)
            throws UserException, LoginException {
        CurrentUserSession activeClient = clientService.logIntoAccount(dto);

        return new ResponseEntity<CurrentUserSession>(activeClient, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutClient(@RequestParam("key") String key) throws UserException, LoginException {
        String activeClient = clientService.logOutFromAccount(key);

        return new ResponseEntity<String>(activeClient, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<Client>> getAllClients(@RequestParam String key)
            throws LoginException, UserException, ClientException {
        List<Client> allClient = clientService.getAllClients(key);

        return new ResponseEntity<List<Client>>(allClient, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientByID(@RequestParam String key, @PathVariable("id") Long id)
            throws ClientException, LoginException {
        Client client = clientService.getClientById(id, key);
        return new ResponseEntity<Client>(client, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClientByID(@RequestParam String key, @PathVariable("id") Long id,
            @RequestBody Client client) throws UserException, ClientException {
        Client clientUpdate = clientService.updateClient(id, client, key);

        return new ResponseEntity<Client>(clientUpdate, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Client> deleteClientByID(@RequestParam String key, @PathVariable("id") Long id)
            throws UserException, LoginException, ClientException {
        Client deleteClient = clientService.deleteClient(id, key);

        return new ResponseEntity<Client>(deleteClient, HttpStatus.OK);
    }

}
