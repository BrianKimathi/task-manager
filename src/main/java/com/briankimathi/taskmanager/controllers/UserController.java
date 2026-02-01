package com.briankimathi.taskmanager.controllers;


import com.briankimathi.taskmanager.dto.LoginRequest;
import com.briankimathi.taskmanager.dto.RegisterRequest;
import com.briankimathi.taskmanager.dto.ResponseDto;
import com.briankimathi.taskmanager.dto.UpdateProfileRequest;
import com.briankimathi.taskmanager.repository.UserRepository;
import com.briankimathi.taskmanager.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class UserController {

    private final UserRepository userRepository;
    private final AuthService authService;

    public UserController(UserRepository userRepository, AuthService authService) {
        this.userRepository = userRepository;
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<Void>> signUp(@RequestBody RegisterRequest registerRequest) {
        ResponseDto<Void> responseDto = authService.register(registerRequest);

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/signin")
    public ResponseEntity<ResponseDto<Void>> login(@RequestBody LoginRequest loginRequest) {
        ResponseDto<Void> responseDto = authService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PutMapping("/profile")
    public ResponseEntity<ResponseDto<Void>> updateProfile(@RequestBody UpdateProfileRequest updateProfileRequest, Authentication authentication) {
        ResponseDto response = authService.updateProfile(updateProfileRequest, authentication);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
