package com.enigma.shopeymart.controller;

import com.enigma.shopeymart.dto.request.AuthRequest;
import com.enigma.shopeymart.dto.response.LoginResponse;
import com.enigma.shopeymart.dto.response.RegisterResponse;
import com.enigma.shopeymart.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping()
    public RegisterResponse response(@RequestBody AuthRequest authRequest) {
        return authService.registerCustomer(authRequest);
    }

    @PostMapping("login")
    public LoginResponse loginResponse(@RequestBody AuthRequest authRequest) {
        return authService.loginCustomer(authRequest);
    }

    @PostMapping("admin")
    public RegisterResponse responseAdmin(@RequestBody AuthRequest authRequest) {
        return authService.registerAdmin(authRequest);
    }

}
