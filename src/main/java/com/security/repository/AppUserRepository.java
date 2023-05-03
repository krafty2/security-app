package com.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.security.models.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long>{
	AppUser findByUsername(String username);
}
