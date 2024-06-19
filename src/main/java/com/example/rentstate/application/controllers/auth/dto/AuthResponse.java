package com.example.rentstate.application.controllers.auth.dto;


import com.example.rentstate.profiles.api.resource.userresource.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String status;
    private String message;
    private String token;
    private UserResponse userResponse;
}
