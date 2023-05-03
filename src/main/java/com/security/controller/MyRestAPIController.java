package com.security.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
public class MyRestAPIController {
	@GetMapping("/infos")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
	@SecurityRequirement(name = "Bearer Authentication")
    public Map<String,Object> dataTest(Principal principal, Authentication authentication){
        return Map.of("name","Compuer");
    }
}
