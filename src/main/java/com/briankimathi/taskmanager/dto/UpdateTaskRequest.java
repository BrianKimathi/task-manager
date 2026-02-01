package com.briankimathi.taskmanager.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateTaskRequest {
    private Long id;
    private String title;
    private String description;
}
