package com.example.rentstate.domain.service.auth;

import com.example.rentstate.application.controllers.auth.dto.AuthResponse;
import com.example.rentstate.application.controllers.auth.dto.LoginRequest;
import com.example.rentstate.application.controllers.auth.dto.RegisterRequest;

public interface AuthService {

    public AuthResponse login(LoginRequest loginRequest);

    public AuthResponse register(RegisterRequest registerRequest) ;
}
