package com.tutorial.learning.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.tutorial.learning.DTO.RegisterRequest;
import com.tutorial.learning.DTO.UserResponse;
import com.tutorial.learning.Entity.UserEntity;
import com.tutorial.learning.Security.jwtUtils;
import com.tutorial.learning.Service.AuthService;

import jakarta.servlet.http.HttpServletResponse;

import java.net.URI;
import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
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

    @PostMapping("/login")
    public ResponseEntity<?> postMethodName(@RequestBody RegisterRequest request, HttpServletResponse response) {
        UserEntity user = authService.authenticate(request);
        String token = jwtUtils.generateToken(user);
        ResponseCookie cookie = ResponseCookie.from("access_cookie", token).httpOnly(false).sameSite("Lax").path("/")
                .maxAge(Duration.ofMinutes(60)).build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        System.out.println(cookie.toString());
        return ResponseEntity.ok("Login sucessfully!");
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> Register(@RequestBody RegisterRequest request) {
        UserResponse response = authService.register(request);
        URI location = URI.create("/api/v1/users/" + response.id());
        return ResponseEntity.created(location).body(response);
    }

}
