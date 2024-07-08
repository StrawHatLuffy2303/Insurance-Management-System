package com.ims.insurance_management_system.Service;

import org.springframework.stereotype.Service;

import com.ims.insurance_management_system.Exception.LoginException;
import com.ims.insurance_management_system.Exception.UserException;
import com.ims.insurance_management_system.Model.CurrentUserSession;
import com.ims.insurance_management_system.Model.LoginDTO;

@Service
public interface ClientService {
   public CurrentUserSession logIntoAccount(LoginDTO dto) throws UserException,LoginException;
   public String logOutFromAccount(String key) throws UserException,LoginException;
}
