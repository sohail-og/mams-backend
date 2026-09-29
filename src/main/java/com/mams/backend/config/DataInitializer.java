package com.mams.backend.config;

import com.mams.backend.entity.User;
import com.mams.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        List<User> users = userRepository.findAll();
        for (User u : users) {
            u.setPasswordHash(passwordEncoder.encode("password"));
            userRepository.save(u);
        }
        System.out.println("✅ All user passwords have been reset to 'password' using the internal encoder.");
    }
}
