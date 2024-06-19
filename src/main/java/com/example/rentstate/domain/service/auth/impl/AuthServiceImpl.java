package com.example.rentstate.domain.service.auth.impl;

import com.example.rentstate.application.controllers.auth.dto.AuthResponse;
import com.example.rentstate.application.controllers.auth.dto.LoginRequest;
import com.example.rentstate.application.controllers.auth.dto.RegisterRequest;
import com.example.rentstate.domain.service.auth.AuthService;
import com.example.rentstate.infrastructure.config.jwt.JwtService;
import com.example.rentstate.profiles.api.resource.userresource.UserResponse;
import com.example.rentstate.profiles.domain.model.aggregates.User;
import com.example.rentstate.profiles.domain.model.valueobjects.Role;
import com.example.rentstate.profiles.domain.service.UserService;
import com.example.rentstate.profiles.infrastructure.persistence.jpa.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), loginRequest.getPassword()));

            Optional<User> user = userService.getByUsername(loginRequest.getUsername());

            if (user.isPresent()) {
                String token = jwtService.getToken(user.get());
                return AuthResponse.builder()
                        .status("success")
                        .message("Login successful")
                        .token(token)
                        .userResponse(new UserResponse(user.get()))
                        .build();
            } else {
                return AuthResponse.builder()
                        .status("error")
                        .message("User not found")
                        .build();
            }
        } catch (AuthenticationException e) {
            return AuthResponse.builder()
                    .status("error")
                    .message("Invalid username or password")
                    .build();
        }
    }

    @Override
    public AuthResponse register(RegisterRequest registerRequest) {

        Optional<User> existingUser = userService.getByUsername(registerRequest.getUsername());
        if (existingUser.isPresent()) {
            return new AuthResponse("error", "Username already exists", null, null);
        }

        User newUser = new User(registerRequest);
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userService.create(newUser);

        String token = jwtService.getToken(newUser);

        return new AuthResponse(
                "success",
                "User registered successfully",
                token,
                new UserResponse(newUser)
        );

    }

}
