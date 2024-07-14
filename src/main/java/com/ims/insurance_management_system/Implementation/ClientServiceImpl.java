package com.ims.insurance_management_system.Implementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ims.insurance_management_system.Exception.ClientException;
import com.ims.insurance_management_system.Exception.LoginException;
import com.ims.insurance_management_system.Exception.UserException;
import com.ims.insurance_management_system.Model.Client;
import com.ims.insurance_management_system.Model.CurrentUserSession;
import com.ims.insurance_management_system.Model.InsurancePolicy;
import com.ims.insurance_management_system.Model.LoginDTO;
import com.ims.insurance_management_system.Repository.AdminRepository;
import com.ims.insurance_management_system.Repository.ClientRepository;
import com.ims.insurance_management_system.Repository.SessionRepository;
import com.ims.insurance_management_system.Service.ClientService;

import jakarta.transaction.Transactional;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepo;

    @Autowired
    private SessionRepository sessionRepo;

    @Autowired
    private AdminRepository adminRepo;

    @Override
    public Client saveClient(Client client) throws ClientException {
        Client existingClient = clientRepo.findByMobileNo(client.getMobileNo());
        if (existingClient != null) {
            throw new ClientException("Mobile number is already registered");
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

    @Override
    public List<Client> getAllClients(String key) throws LoginException, UserException {
        CurrentUserSession activeSession = sessionRepo.findByUuid(key);
        if (activeSession == null) {
            throw new LoginException("Please login first");
        }

        if (adminRepo.existsById(activeSession.getCurrentUserID()) && activeSession.getRole().equals("ADMIN")) {

            return clientRepo.findAll();

        } else {
            throw new UserException("Only admin can view all clients");
        }

    }

    @Override
    public Client getClientById(Long id, String key) throws ClientException, LoginException {
        CurrentUserSession activeSession = sessionRepo.findByUuid(key);
        if (activeSession == null) {
            throw new LoginException("Please login first");
        }

        if (clientRepo.findById(id).isEmpty()) {
            throw new ClientException("Invalid client id");
        } else {
            return clientRepo.findById(id).get();
        }

    }

    @Override
    @Transactional(rollbackOn = { UserException.class, ClientException.class })
    public Client updateClient(Long id, Client client, String key) throws UserException, ClientException {
        CurrentUserSession activeSession = sessionRepo.findByUuid(key);
        if (activeSession == null) {
            throw new UserException("Please login to update client profile");
        }

        if (clientRepo.findById(id).isEmpty()) {
            throw new ClientException("Client not found");
        }
        Client existingClient = clientRepo.findById(id).get();
        existingClient.setFirstName(client.getFirstName());
        existingClient.setMiddleName(client.getMiddleName());
        existingClient.setLastName(client.getLastName());
        existingClient.setAddress(client.getAddress());
        existingClient.setDob(client.getDob());
        existingClient.setPassword(client.getPassword());

        if (activeSession.getRole().equals("ADMIN")) {
            if (client.getMobileNo() != null) {
                existingClient.setMobileNo(client.getMobileNo());
            }
        } else {
            if (existingClient.getMobileNo() != null && existingClient.getMobileNo().equals(client.getMobileNo())) {
                // do nothing, mobile number is same as existing one
            } else {
                throw new UserException("Currently Client not allowed to update mobile number(Only Admin Can Change)");
            }
        }

        return clientRepo.save(existingClient);
    }

    @Override
    @Transactional
    public Client deleteClient(Long id, String key) throws UserException, LoginException {
        CurrentUserSession activeSession = sessionRepo.findByUuid(key);
        if (activeSession == null) {
            throw new LoginException("No active session found, please login");
        }
        if (!adminRepo.findById(activeSession.getCurrentUserID()).isPresent()
                && !activeSession.getRole().equals("ADMIN")) {
            throw new UserException("Only admin can delete any client");
        }

        Client client = clientRepo.findById(id).orElse(null);
        if (client == null) {
            throw new UserException("Client with id " + id + " not found");
        }

        if (client.getInsurancePolicies() != null) {
            for (InsurancePolicy policy : client.getInsurancePolicies()) {
                policy.getClients().remove(client);
            }

            client.setInsurancePolicies(null);
        }

        clientRepo.delete(client);

        return client;
    }

}
