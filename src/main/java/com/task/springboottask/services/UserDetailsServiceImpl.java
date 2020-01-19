package com.task.springboottask.services;

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
    private GrantedAuthority adminRole = new SimpleGrantedAuthority("admin");
    private GrantedAuthority readOnlyRole = new SimpleGrantedAuthority("readOnly");

    private List<UserDetails> users = Arrays.asList(
            new User("admin", "root", Collections.singletonList(adminRole)),
            new User("client", "client", Collections.singletonList(readOnlyRole)));

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return users.stream()
                .filter(user -> user.getUsername().equals(name))
                .findFirst()
                .orElse(null);
    }
}
