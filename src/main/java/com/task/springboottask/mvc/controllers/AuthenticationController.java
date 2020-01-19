package com.task.springboottask.mvc.controllers;

import com.task.springboottask.rest.TokenResponse;
import com.task.springboottask.services.TokenGeneration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenGeneration tokenGeneration;
    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping(value = "/authenticate")
    public ResponseEntity createAuthenticationToken(@RequestParam(value = "username") String name,
                                                                   @RequestParam(value = "password") String password) {
        log.info(String.format("Authentication request has received from user %s", name));
        Authentication authenticate = authenticate(name, password);
        if (authenticate != null) {
            String token = tokenGeneration.generateToken(userDetailsService.loadUserByUsername(name));
            return ResponseEntity.ok(new TokenResponse(token));
        } else {
            return ResponseEntity.ok("Wrong credentials");
        }
    }

    private Authentication authenticate(String userName, String password) {
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
        } catch (DisabledException e) {
            log.error("User is disabled");
        } catch (BadCredentialsException e) {
            log.error("Invalid login/password");
        }
        return authentication;
    }
}
