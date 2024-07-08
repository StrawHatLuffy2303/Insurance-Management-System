package com.ims.insurance_management_system.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ims.insurance_management_system.Model.CurrentUserSession;

@Repository
public interface SessionRepository extends JpaRepository<CurrentUserSession,Long> {
   public  CurrentUserSession  findByUuid(String uuid);
}
