package com.security.controller;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.security.dto.LoginRequest;
import com.security.models.AppUser;
import com.security.service.AccountService;
import com.security.service.TokenService;

@RestController
public class AuthController {

	 private static final Logger LOGGER= LoggerFactory.getLogger(AuthController.class);
	 private TokenService tokenService;
	 private AccountService accountService;
	 private JwtDecoder jwtDecoder;
	 private AuthenticationManager authenticationManager;
	 
	public AuthController(TokenService tokenService, AccountService accountService, JwtDecoder jwtDecoder,
			AuthenticationManager authenticationManager) {
		super();
		this.tokenService = tokenService;
		this.accountService = accountService;
		this.jwtDecoder = jwtDecoder;
		this.authenticationManager = authenticationManager;
	}
	 
	@PostMapping("/token")
    public ResponseEntity<Map<String,String>> requestForToken(
    		LoginRequest loginRequest,
    		@RequestParam String grantType,
            @RequestParam String  username,
            @RequestParam String  password,
            @RequestParam boolean withRefreshToken,
            String refreshToken
    		){
		
        Map<String,String > response;
        if(grantType.equals("password")){
            Authentication authentication=authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username,password
                    )
            );
            response=tokenService.generateJwtToken(authentication.getName(),authentication.getAuthorities(),withRefreshToken);
            return ResponseEntity.ok(response);
        } else if(grantType.equals("refreshToken")){
            String refreshTokens=refreshToken;
            if(refreshTokens==null) {
                return new ResponseEntity<>(Map.of("error","RefreshToken Not Present"),HttpStatus.UNAUTHORIZED);
            }
            Jwt decodedJwt = jwtDecoder.decode(refreshTokens);
            String username1=decodedJwt.getSubject();
            AppUser appUser=accountService.findByUsername(username1);
            Collection<GrantedAuthority> authorities=appUser.getRoles()
                        .stream()
                        .map(role->new SimpleGrantedAuthority(role.getRoleName()))
                        .collect(Collectors.toList());
            response=tokenService.generateJwtToken(appUser.getUsername(),authorities,withRefreshToken);
            return ResponseEntity.ok(response);
        }
        return new ResponseEntity<Map<String, String>>(Map.of("error",String.format("grantType <<%s>> not supported ",grantType)),HttpStatus.UNAUTHORIZED);
    } 
}
