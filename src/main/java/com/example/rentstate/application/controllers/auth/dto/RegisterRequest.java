package com.example.rentstate.application.controllers.auth.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Data
@Getter
@NoArgsConstructor
public class RegisterRequest {
    private String name;
    private String lastName;
    private String username;
    private String password;
}
