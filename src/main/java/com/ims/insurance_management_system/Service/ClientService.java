package com.ims.insurance_management_system.Service;

import java.util.List;

import com.ims.insurance_management_system.Exception.ClientException;
import com.ims.insurance_management_system.Exception.LoginException;
import com.ims.insurance_management_system.Exception.UserException;
import com.ims.insurance_management_system.Model.Client;
import com.ims.insurance_management_system.Model.CurrentUserSession;
import com.ims.insurance_management_system.Model.LoginDTO;

public interface ClientService {
    public Client saveClient(Client client) throws ClientException;

    List<Client> getAllClients(String key) throws LoginException,UserException;

    public Client getClientById(Long id, String key) throws ClientException,LoginException;

    public Client updateClient(Long id, Client client, String key) throws UserException,ClientException ;

    public Client deleteClient(Long id, String key) throws UserException,LoginException;

    public CurrentUserSession logIntoAccount(LoginDTO dto) throws UserException, LoginException;

    public String logOutFromAccount(String key) throws UserException, LoginException;

}
