package com.briankimathi.taskmanager.controllers;

import com.briankimathi.taskmanager.dto.CreateTaskRequest;
import com.briankimathi.taskmanager.dto.ResponseDto;
import com.briankimathi.taskmanager.dto.UpdateTaskRequest;
import com.briankimathi.taskmanager.services.TaskService;
import org.hibernate.sql.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<ResponseDto<Void>> createTask(
            @RequestBody CreateTaskRequest  createTaskRequest,
            Authentication authentication
    ) {
        ResponseDto<Void> responseDto = taskService.createTask(createTaskRequest, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<Void>> getAllTasks(Authentication authentication) {
        ResponseDto<Void> responseDto = taskService.getAllTasks(authentication);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> updateTask(
            @PathVariable Long id,
            @RequestBody UpdateTaskRequest taskRequest,
            Authentication authentication
    ) {
        ResponseDto<Void> responseDto = taskService.updateTask(id, taskRequest, authentication);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteTask(@PathVariable Long id, Authentication authentication) {
        ResponseDto<Void> responseDto = taskService.deleteTask(id, authentication);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

}
