package com.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.security.models.AppRole;
import com.security.models.AppUser;
import com.security.service.AccountService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
public class UserController {
	@Autowired
	private AccountService accountService;
	
	
	@PostMapping("/public/newUser")
	@SecurityRequirement(name = "Bearer Authentication")
	public String enregistrerUser(String roleName,
			String userName,String email,
			String password) {
		
		AppUser user = new AppUser();
		
		user.setUsername(userName);
		user.setEmail(email);
		user.setPassword(password);
		
		AppRole appRole = accountService.findByRoleName(roleName);
		
		user.getRoles().add(appRole);
		accountService.newUser(user);
		return "home";
	}
	
	@PostMapping("/public/newRole")
	public String enregistrerRole(String roleName) {
		
		AppRole role =  new AppRole();
		
		role.setRoleName(roleName);
		
		accountService.newRole(role);
		
		return "home";
	}
}
