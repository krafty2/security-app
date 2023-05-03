package com.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.security.models.AppRole;

@Repository
public interface AppRoleRepository extends JpaRepository<AppRole, Long>{
	AppRole findByRoleName(String roleName);
}
