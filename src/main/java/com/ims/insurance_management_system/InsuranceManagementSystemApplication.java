package com.ims.insurance_management_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class InsuranceManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(InsuranceManagementSystemApplication.class, args);
	}

}
