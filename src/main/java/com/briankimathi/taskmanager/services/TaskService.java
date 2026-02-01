package com.briankimathi.taskmanager.services;

import com.briankimathi.taskmanager.dto.CreateTaskRequest;
import com.briankimathi.taskmanager.dto.ResponseDto;
import com.briankimathi.taskmanager.dto.UpdateTaskRequest;
import com.briankimathi.taskmanager.models.Task;
import com.briankimathi.taskmanager.models.User;
import com.briankimathi.taskmanager.repository.TaskRepository;
import com.briankimathi.taskmanager.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public ResponseDto<Void> createTask(CreateTaskRequest taskRequest, Authentication authentication) {
        if(
                taskRequest.getTitle() == null
                || taskRequest.getTitle().isEmpty()
                        || taskRequest.getDescription() == null
                || taskRequest.getDescription().isEmpty()
        ) {
            return new ResponseDto(
                    "failed",
                    "Task Title and Description can not be empty",
                    null
            );
        }

        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setUser(user);
        taskRepository.save(task);

        return new ResponseDto(
                "success",
                "Task created successfully",
                task
        );

    }

    public ResponseDto<Void> getAllTasks(Authentication authentication) {
        String email = authentication.getName();
        Optional<User> user = userRepository.findByEmail(email);
        if(!user.isPresent()) {
            return new ResponseDto(
                    "failed",
                    "User not found",
                    null
            );
        }

        List<Task> tasks = taskRepository.findByUserEmail(email);

        return new ResponseDto(
                "success",
                "Tasks retrieved successfully",
                tasks
        );

    }

    public ResponseDto<Void> updateTask(UpdateTaskRequest updateTaskRequest, Authentication authentication) {
        String email = authentication.getName();
        Optional<User> user = userRepository.findByEmail(email);

        if(!user.isPresent()) {
            return new ResponseDto(
                    "failed",
                    "User not found",
                    null
            );
        }

        Task task = taskRepository.findByIdAndUserEmail(updateTaskRequest.getId(), email).orElseThrow(
                () -> new RuntimeException("Task not found")
        );

        if (!task.getUser().getEmail().equals(user.get().getEmail())) {
            return new ResponseDto(
                    "failed",
                    "Cannot update a task you don't own!",
                    null
            );
        }

        task.setTitle(updateTaskRequest.getTitle());
        task.setDescription(updateTaskRequest.getDescription());

        taskRepository.save(task);

        return new ResponseDto(
                "success",
                "Task updated successfully",
                task
        );

    }


    public ResponseDto<Void> deleteTask(Long id, Authentication authentication) {
        String email = authentication.getName();

        Optional<User> user = userRepository.findByEmail(email);

        if(!user.isPresent()) {
            return new ResponseDto(
                    "failed",
                    "User not found!",
                    null
            );
        }

        Task task = taskRepository.findByIdAndUserEmail(id, email).orElseThrow(
                () -> new RuntimeException("Task not found")
        );

        taskRepository.delete(task);

        return new ResponseDto(
                "success",
                "Task deleted successfully",
                null
        );

    }

}
