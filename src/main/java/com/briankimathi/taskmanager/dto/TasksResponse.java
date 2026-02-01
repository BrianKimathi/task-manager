package com.briankimathi.taskmanager.dto;

import com.briankimathi.taskmanager.models.Task;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class TasksResponse {
    List<Task> tasks;
}
