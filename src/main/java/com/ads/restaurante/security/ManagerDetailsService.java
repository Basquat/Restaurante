package com.ads.restaurante.security;

import com.ads.restaurante.model.Manager;
import com.ads.restaurante.repository.ManagerRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/** Carrega o Manager pelo username para o Spring Security. Todo manager tem o papel ROLE_MANAGER. */
@Service
public class ManagerDetailsService implements UserDetailsService {

    private final ManagerRepository repository;

    public ManagerDetailsService(ManagerRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Manager manager = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Manager '" + username + "' não encontrado"));
        return User.withUsername(manager.getUsername())
                .password(manager.getPassword())
                .authorities("ROLE_MANAGER")
                .build();
    }
}
