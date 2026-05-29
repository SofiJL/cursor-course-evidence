package com.example.taskflow.mapper;

import com.example.taskflow.dto.TaskResponse;
import com.example.taskflow.dto.TaskSummaryResponse;
import com.example.taskflow.model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public TaskResponse toResponse(Task task) {
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus());
        response.setCreatedAt(task.getCreatedAt());
        response.setUpdatedAt(task.getUpdatedAt());
        return response;
    }

    public TaskSummaryResponse toSummary(Task task) {
        TaskSummaryResponse response = new TaskSummaryResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setStatus(task.getStatus());
        return response;
    }
}
