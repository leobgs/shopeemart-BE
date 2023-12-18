package com.enigma.shopeymart.service.impl;

import com.enigma.shopeymart.constant.ERole;
import com.enigma.shopeymart.dto.request.AuthRequest;
import com.enigma.shopeymart.dto.response.LoginResponse;
import com.enigma.shopeymart.dto.response.RegisterResponse;
import com.enigma.shopeymart.entity.*;
import com.enigma.shopeymart.repository.AdminRepository;
import com.enigma.shopeymart.repository.UserCredentialsRepository;
import com.enigma.shopeymart.security.JwtUtil;
import com.enigma.shopeymart.service.AuthService;
import com.enigma.shopeymart.service.CustomerService;
import com.enigma.shopeymart.service.RoleService;
import com.enigma.shopeymart.util.ValidationUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserCredentialsRepository userCredentialsRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerService customerService;
    private final AdminRepository adminRepository;
    private final RoleService roleService;
    private final ValidationUtil validationUtil;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public RegisterResponse registerCustomer(AuthRequest request) {
        try {
            //TODO 1 : Set Role
            Role role = Role.builder()
                    .name(ERole.ROLE_CUSTOMER)
                    .build();
            role = roleService.getOrSave(role);

            //TODO 2 : Set Credential
            UserCredentials userCredentials = UserCredentials.builder()
                    .username(request.getUsername().toLowerCase())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(role)
                    .build();
            userCredentialsRepository.saveAndFlush(userCredentials);
            //TODO 3: set Customer
            Customer customer = Customer.builder()
                    .userCredentials(userCredentials)
                    .email(request.getEmail())
                    .name(request.getCustomerName())
                    .phone(request.getMobilePhone())
                    .build();
            customerService.createNewCustomer(customer);
            return RegisterResponse.builder()
                    .username(userCredentials.getUsername())
                    .role(userCredentials.getRole().getName().toString())
                    .build();
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User Already Exist");
        }
    }

    @Override
    public LoginResponse loginCustomer(AuthRequest authRequest) {
        //tempat untuk logic login
        validationUtil.setValidator(authRequest);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequest.getUsername().toLowerCase(),
                authRequest.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //object AppUser
        AppUser appUser = (AppUser) authentication.getPrincipal();
        String token = jwtUtil.generateToken(appUser);
        return LoginResponse.builder()
                .token(token)
                .role(appUser.getRole().name())
                .build();
    }

    @Override
    public RegisterResponse registerAdmin(AuthRequest authRequest) {
        try {
            //TODO 1 : Set Role
            Role role = Role.builder()
                    .name(ERole.ROLE_ADMIN)
                    .build();
            role = roleService.getOrSave(role);

            //TODO 2 : Set Credential
            UserCredentials userCredentials = UserCredentials.builder()
                    .username(authRequest.getUsername().toLowerCase())
                    .password(passwordEncoder.encode(authRequest.getPassword()))
                    .role(role)
                    .build();
            userCredentialsRepository.saveAndFlush(userCredentials);

            //TODO 3 : Set Admin
            Admin admin = Admin.builder()
                    .userCredentials(userCredentials)
                    .email(authRequest.getEmail())
                    .name(authRequest.getCustomerName())
                    .phoneNumber(authRequest.getMobilePhone())
                    .build();
            adminRepository.save(admin);
            return RegisterResponse.builder()
                    .username(userCredentials.getUsername())
                    .role(userCredentials.getRole().getName().toString())
                    .build();
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ngademin Already exist");
        }
    }

}
