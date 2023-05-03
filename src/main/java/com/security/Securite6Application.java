package com.security;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.security.config.RsaKeyProperties;
import com.security.models.AppRole;
import com.security.models.AppUser;
import com.security.service.AccountService;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class Securite6Application {

	public static void main(String[] args) {
		SpringApplication.run(Securite6Application.class, args);
	}

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    KeyPair keyPair() throws NoSuchAlgorithmException, IOException {
        KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance("RSA");
        var keyPair=keyPairGenerator.generateKeyPair();
        return keyPair;
    }
    
    @Bean
    CommandLineRunner start(AccountService accountService, PasswordEncoder passwordEncoder){
        return args -> {
        	AppRole user = new AppRole("USER");
        	accountService.newRole(user);
        	AppRole admin = new AppRole("ADMIN");
        	accountService.newRole(admin);
        	
        	String userRole = "USER";
        	String adminRole = "ADMIN";
        	
        	AppRole findUserRole = accountService.findByRoleName(userRole);
        	AppRole findAdminRole = accountService.findByRoleName(userRole);
        	
        	AppUser user1 = new AppUser();
        	user1.setUsername("user1");
        	user1.setPassword(passwordEncoder.encode("1234"));
        	user1.setEmail("steeve@gmail.com");
        	user1.getRoles().add(findUserRole);
        	accountService.newUser(user1);
        	
        	
        	AppUser admin1 = new AppUser();
        	admin1.setUsername("admin");
        	admin1.setPassword(passwordEncoder.encode("1015"));
        	admin1.setEmail("admin@gmail.com");
        	admin1.getRoles().add(findAdminRole);
        	accountService.newUser(admin1);
        };
    }
}
