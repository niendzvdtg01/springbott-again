package com.tutorial.learning.Service;

import java.util.Locale;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tutorial.learning.DTO.RegisterRequest;
import com.tutorial.learning.DTO.UserResponse;
import com.tutorial.learning.Entity.UserEntity;
import com.tutorial.learning.Enum.UserRole;
import com.tutorial.learning.Repository.UserRepository;

import jakarta.transaction.Transactional;

@Service 
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;


    public AuthService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Transactional 
    public UserResponse register(RegisterRequest request){
        String nomaliziedEmail = request.email().trim().toLowerCase(Locale.ROOT);
        if(userRepository.existsByEmail(nomaliziedEmail)){
            throw new RuntimeException("Error!!!!");
        }
        UserEntity user = new UserEntity();
        user.setEmail(nomaliziedEmail);
        user.setPasswordHash(encoder.encode(request.password()));
        user.setRole(UserRole.USER);
        userRepository.save(user);
        return new UserResponse(user.getId(), user.getEmail(), user.getRole().name());
    }
}
