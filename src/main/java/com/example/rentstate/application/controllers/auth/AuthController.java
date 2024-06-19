package com.example.rentstate.application.controllers.auth;
import com.example.rentstate.domain.service.auth.AuthService;
import com.example.rentstate.application.controllers.auth.dto.AuthResponse;
import com.example.rentstate.application.controllers.auth.dto.LoginRequest;
import com.example.rentstate.application.controllers.auth.dto.RegisterRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/api/v1")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(
                authService.login(loginRequest)
        );
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(
                authService.register(registerRequest)
        );
    }

}
