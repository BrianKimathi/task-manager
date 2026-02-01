package com.briankimathi.taskmanager.controllers;

import com.briankimathi.taskmanager.dto.CreateTaskRequest;
import com.briankimathi.taskmanager.dto.ResponseDto;
import com.briankimathi.taskmanager.dto.UpdateTaskRequest;
import com.briankimathi.taskmanager.services.TaskService;
import org.hibernate.sql.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    public ResponseEntity<ResponseDto<Void>> createTask(
            @RequestBody CreateTaskRequest  createTaskRequest,
            Authentication authentication
    ) {
        ResponseDto<Void> responseDto = taskService.createTask(createTaskRequest, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    public ResponseEntity<ResponseDto<Void>> getAllTasks(Authentication authentication) {
        ResponseDto<Void> responseDto = taskService.getAllTasks(authentication);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    public ResponseEntity<ResponseDto<Void>> updateTask(
            @RequestBody UpdateTaskRequest taskRequest,
            Authentication authentication
    ) {
        ResponseDto<Void> responseDto = taskService.updateTask(taskRequest, authentication);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    public ResponseEntity<ResponseDto<Void>> deleteTask(@RequestParam Long id, Authentication authentication) {
        ResponseDto<Void> responseDto = taskService.deleteTask(id, authentication);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

}
