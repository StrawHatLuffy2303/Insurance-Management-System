package com.ims.insurance_management_system.Implementation;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ims.insurance_management_system.Exception.LoginException;
import com.ims.insurance_management_system.Exception.UserException;
import com.ims.insurance_management_system.Model.Client;
import com.ims.insurance_management_system.Model.CurrentUserSession;
import com.ims.insurance_management_system.Model.LoginDTO;
import com.ims.insurance_management_system.Repository.ClientRepository;
import com.ims.insurance_management_system.Repository.SessionRepository;
import com.ims.insurance_management_system.Service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepo;

    @Autowired
    private SessionRepository sessionRepo;

    @Override
    public Client saveClient(Client client) throws UserException, LoginException {
        Client existingClient = clientRepo.findByMobileNo(client.getMobileNo());
        if (existingClient != null) {
            throw new LoginException("Mobile number is already registered");
        } else {
            return clientRepo.save(client);
        }

    }

    @Override
    public CurrentUserSession logIntoAccount(LoginDTO dto) throws UserException, LoginException {
        Client existingClient = clientRepo.findByMobileNo(dto.getMobileNo());
        if (existingClient == null) {
            throw new LoginException("Mobile number not found in our records");
        }

        if (!dto.getPassword().equals(existingClient.getPassword())) {
            throw new LoginException("Wrong Password");
        }

        if (sessionRepo.existsById(existingClient.getId())) {
            throw new LoginException("You have already logged in");
        }

        String uniqueKey = UUID.randomUUID().toString().replace("-", "").substring(0, 7);
        CurrentUserSession newCurrentUser = new CurrentUserSession(existingClient.getId(), "CLIENT", uniqueKey,
                LocalDateTime.now());
        sessionRepo.save(newCurrentUser);
        return newCurrentUser;
    }

    @Override
    public String logOutFromAccount(String key) throws UserException, LoginException {
        CurrentUserSession activeSession = sessionRepo.findByUuid(key);
        if (activeSession == null) {
            throw new LoginException("No Active Session Found");
        } else {
            sessionRepo.delete(activeSession);
            return "Logout SuccessFully";
        }

    }

}
