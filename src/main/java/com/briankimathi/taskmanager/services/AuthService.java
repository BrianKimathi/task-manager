package com.briankimathi.taskmanager.services;


import com.briankimathi.taskmanager.dto.LoginRequest;
import com.briankimathi.taskmanager.dto.LoginResponse;
import com.briankimathi.taskmanager.dto.RegisterRequest;
import com.briankimathi.taskmanager.dto.ResponseDto;
import com.briankimathi.taskmanager.models.User;
import com.briankimathi.taskmanager.repository.UserRepository;
import com.briankimathi.taskmanager.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Locale;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil,
            AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public ResponseDto<Void> register(RegisterRequest registerRequest) {
        if (registerRequest.getEmail() == null || registerRequest.getUsername() == null || registerRequest.getPassword() == null) {
            return new ResponseDto(
                    "failed",
                    "All fields required",
                    null
            );
        }

        if(userRepository.existsByEmail(registerRequest.getEmail())) {
            return new ResponseDto(
                    "failed",
                    "User with email already exists",
                    null
            );
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        userRepository.save(user);

        return new ResponseDto(
                "success",
                "User registered successfully",
                null
        );
    }

    public ResponseDto<Void> login(LoginRequest loginRequest) {
        if(loginRequest.getEmail() == null || loginRequest.getPassword() == null) {
            return new ResponseDto(
                    "failed",
                    "All details required",
                    null
            );
        }

        if(!userRepository.existsByEmail(loginRequest.getEmail())) {
            return new ResponseDto(
                    "failed",
                    "User not found!",
                    null
            );
        }

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found!"));

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return new ResponseDto(
                    "failed",
                    "Invalid Credentials!",
                    null
            );
        }

        // login user and generate jwt
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                loginRequest.getEmail(),
                "",
                new ArrayList<>()
        );

        String jwt = jwtUtil.generateToken(userDetails);

        return new ResponseDto(
                "success",
                "User logged in successfully",
                new LoginResponse(
                        user.getUsername(),
                        user.getEmail(),
                        jwt
                )
        );

    }

}
