package com.task.springboottask.services;

import com.task.springboottask.mvc.model.UserRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Value("${admin.name}")
    private String adminName;

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${client.name}")
    private String clientName;

    @Value("${client.password}")
    private String clientPassword;

    private List<UserDetails> users;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        loadUsers();
        return users.stream()
                .filter(user -> user.getUsername().equals(name))
                .findFirst()
                .orElse(null);
    }

    private void loadUsers() {
        if (users == null) {
            users = Arrays.asList(
                    new User(adminName, adminPassword, Collections.singletonList(new SimpleGrantedAuthority(UserRole.ADMIN))),
                    new User(clientName, clientPassword, Collections.singletonList(new SimpleGrantedAuthority(UserRole.READ_ONLY))));
        }
    }
}
