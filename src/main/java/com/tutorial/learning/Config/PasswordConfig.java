package com.tutorial.learning.Config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration 
public class PasswordConfig {
    @Bean 
    PasswordEncoder passwordEncoder(){
        String currentAlgorithm = "argon2id-v1";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(currentAlgorithm, new Argon2PasswordEncoder(16, 32, 1, 19456, 2));

        encoders.put("bcrypt",new BCryptPasswordEncoder());
        
        return new DelegatingPasswordEncoder(currentAlgorithm, encoders);
    }

}
