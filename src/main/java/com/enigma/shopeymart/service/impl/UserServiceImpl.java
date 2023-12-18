package com.enigma.shopeymart.service.impl;

import com.enigma.shopeymart.entity.AppUser;
import com.enigma.shopeymart.entity.UserCredentials;
import com.enigma.shopeymart.repository.UserCredentialsRepository;
import com.enigma.shopeymart.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserCredentialsRepository userCredentialsRepository;

    @Override
    public AppUser loadUserByUserId(String id) {
        UserCredentials userCredentials = userCredentialsRepository.findById(id)
                .orElseThrow(()->new UsernameNotFoundException("Invalid Credential"));
        return AppUser.builder()
                .id(userCredentials.getId())
                .username(userCredentials.getUsername())
                .password(userCredentials.getPassword())
                .role(userCredentials.getRole().getName())
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCredentials userCredentials = userCredentialsRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("Invalid credential"));
        return AppUser.builder()
                .id(userCredentials.getId())
                .username(userCredentials.getUsername())
                .password(userCredentials.getPassword())
                .role(userCredentials.getRole().getName())
                .build();
    }
}
