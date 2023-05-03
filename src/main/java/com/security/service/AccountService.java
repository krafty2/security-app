package com.security.service;

import org.springframework.stereotype.Service;

import com.security.models.AppRole;
import com.security.models.AppUser;
import com.security.repository.AppRoleRepository;
import com.security.repository.AppUserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AccountService {
	private AppUserRepository appUserRepository;
	private AppRoleRepository appRoleRepository;
	
	public AccountService(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository) {
		super();
		this.appUserRepository = appUserRepository;
		this.appRoleRepository = appRoleRepository;
	}
	
	public AppUser newUser(AppUser appUser) {
		return appUserRepository.save(appUser);
	}
	
	public AppRole newRole(AppRole appRole) {
		return appRoleRepository.save(appRole);
	}
	
	public void addRoleToUser(String username,String roleName) {
		AppUser appUser = appUserRepository.findByUsername(username);
		AppRole appRole = appRoleRepository.findByRoleName(roleName);
		appUser.getRoles().add(appRole);
	}
	
	public AppUser findByUsername(String username) {
		return appUserRepository.findByUsername(username);
	}
	
	public AppRole findByRoleName(String roleName) {
		return appRoleRepository.findByRoleName(roleName);
	}
}
