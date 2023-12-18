package com.enigma.shopeymart.service;

import com.enigma.shopeymart.dto.request.AuthRequest;
import com.enigma.shopeymart.dto.response.LoginResponse;
import com.enigma.shopeymart.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse registerCustomer(AuthRequest request);

    LoginResponse loginCustomer(AuthRequest authRequest);

    RegisterResponse registerAdmin(AuthRequest authRequest);

}
