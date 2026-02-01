package com.briankimathi.taskmanager.dto;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateProfileRequest {
    private String username;
    private String oldPassword;
    private String newPassword;
}
