package com.ims.insurance_management_system.Service;

import com.ims.insurance_management_system.Exception.UserException;
import com.ims.insurance_management_system.Model.Admin;


public interface AdminService {
    public Admin registerAdmin(Admin admin) throws UserException;

}
