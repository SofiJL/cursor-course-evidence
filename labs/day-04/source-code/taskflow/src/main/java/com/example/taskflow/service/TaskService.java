package com.example.taskflow.service;

import com.example.taskflow.dto.CreateTaskRequest;
import com.example.taskflow.dto.DeleteTaskResponse;
import com.example.taskflow.dto.TaskResponse;
import com.example.taskflow.dto.TaskSummaryResponse;
import com.example.taskflow.dto.UpdateStatusRequest;
import com.example.taskflow.exception.InvalidStatusException;
import com.example.taskflow.exception.TaskNotFoundException;
import com.example.taskflow.mapper.TaskMapper;
import com.example.taskflow.model.Task;
import com.example.taskflow.model.TaskStatus;
import com.example.taskflow.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public TaskResponse createTask(CreateTaskRequest request) {
        LocalDateTime now = LocalDateTime.now();

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(TaskStatus.PENDING);
        task.setCreatedAt(now);
        task.setUpdatedAt(now);

        Task saved = taskRepository.save(task);
        return taskMapper.toResponse(saved);
    }

    public List<TaskSummaryResponse> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(taskMapper::toSummary)
                .toList();
    }

    public TaskResponse getTaskById(Long id) {
        Task task = findTaskOrThrow(id);
        return taskMapper.toResponse(task);
    }

    public TaskResponse updateTaskStatus(Long id, UpdateStatusRequest request) {
        Task task = findTaskOrThrow(id);
        TaskStatus newStatus = parseStatus(request.getStatus());

        task.setStatus(newStatus);
        task.setUpdatedAt(LocalDateTime.now());

        Task updated = taskRepository.save(task);
        return taskMapper.toResponse(updated);
    }

    public DeleteTaskResponse deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }
        taskRepository.deleteById(id);
        return new DeleteTaskResponse("Task deleted successfully");
    }

    private Task findTaskOrThrow(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    private TaskStatus parseStatus(String status) {
        if (status == null) {
            throw new InvalidStatusException("null");
        }
        try {
            return TaskStatus.valueOf(status.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new InvalidStatusException(status);
        }
    }
}
