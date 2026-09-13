package com.tutorial.learning.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.tutorial.learning.DTO.RegisterRequest;
import com.tutorial.learning.DTO.UserResponse;
import com.tutorial.learning.Service.AuthService;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController 
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> Register(@RequestBody RegisterRequest request) {
        UserResponse response = authService.register(request);
        URI location = URI.create("/api/v1/users/" + response.id());
        return  ResponseEntity.created(location).body(response);
    }
    
}
