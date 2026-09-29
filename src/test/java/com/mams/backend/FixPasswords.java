package com.mams.backend;

import com.mams.backend.entity.User;
import com.mams.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
public class FixPasswords {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void updatePasswords() {
        List<User> users = userRepository.findAll();
        for (User u : users) {
            u.setPasswordHash(passwordEncoder.encode("password"));
            userRepository.save(u);
        }
    }
}
