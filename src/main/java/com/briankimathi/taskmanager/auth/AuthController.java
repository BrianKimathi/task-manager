package com.briankimathi.taskmanager.auth;

import com.briankimathi.taskmanager.user.User;
import com.briankimathi.taskmanager.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    public final UserRepository userRepository;
    public final PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public String signUp(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully";
    }

    @PostMapping("/signin")
    public String signIn() {
        return "Login implementation coming soon!";
    }

}
