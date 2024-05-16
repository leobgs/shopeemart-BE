package com.enigma.shopeymart.controller;

import com.enigma.shopeymart.dto.request.AuthRequest;
import com.enigma.shopeymart.dto.response.LoginResponse;
import com.enigma.shopeymart.dto.response.RegisterResponse;
import com.enigma.shopeymart.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("login")
    public LoginResponse loginResponse(@RequestBody AuthRequest authRequest) {
        return authService.loginCustomer(authRequest);
    }

    @PostMapping("register/admin")
    public RegisterResponse responseAdmin(@RequestBody AuthRequest authRequest) {
        return authService.registerAdmin(authRequest);
    }

    @PostMapping("register/customer")
    public RegisterResponse response(@RequestBody AuthRequest authRequest) {
        return authService.registerCustomer(authRequest);
    }

}
