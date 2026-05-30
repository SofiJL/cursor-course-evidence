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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskService taskService;

    private Task sampleTask;
    private TaskResponse sampleResponse;

    @BeforeEach
    void setUp() {
        sampleTask = buildTask(1L, "Preparar documentación", TaskStatus.PENDING);
        sampleResponse = buildTaskResponse(sampleTask);
    }

    @Test
    void createTask_setsPendingStatusAndDates() {
        CreateTaskRequest request = new CreateTaskRequest();
        request.setTitle("Preparar documentación");
        request.setDescription("Generar documentación técnica");

        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> {
            Task task = invocation.getArgument(0);
            task.setId(1L);
            return task;
        });
        when(taskMapper.toResponse(any(Task.class))).thenReturn(sampleResponse);

        TaskResponse result = taskService.createTask(request);

        ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepository).save(captor.capture());

        Task saved = captor.getValue();
        assertEquals("Preparar documentación", saved.getTitle());
        assertEquals("Generar documentación técnica", saved.getDescription());
        assertEquals(TaskStatus.PENDING, saved.getStatus());
        assertNotNull(saved.getCreatedAt());
        assertNotNull(saved.getUpdatedAt());
        assertEquals(saved.getCreatedAt(), saved.getUpdatedAt());
        assertEquals(sampleResponse, result);
    }

    @Test
    void getAllTasks_returnsSummaries() {
        TaskSummaryResponse summary = new TaskSummaryResponse();
        summary.setId(1L);
        summary.setTitle("Preparar documentación");
        summary.setStatus(TaskStatus.PENDING);

        when(taskRepository.findAll()).thenReturn(List.of(sampleTask));
        when(taskMapper.toSummary(sampleTask)).thenReturn(summary);

        List<TaskSummaryResponse> result = taskService.getAllTasks();

        assertEquals(1, result.size());
        assertEquals(summary, result.getFirst());
    }

    @Test
    void getTaskById_returnsTaskWhenFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));
        when(taskMapper.toResponse(sampleTask)).thenReturn(sampleResponse);

        TaskResponse result = taskService.getTaskById(1L);

        assertEquals(sampleResponse, result);
    }

    @Test
    void getTaskById_throwsWhenNotFound() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(99L));
    }

    @Test
    void updateTaskStatus_updatesStatusAndUpdatedAt() {
        UpdateStatusRequest request = new UpdateStatusRequest();
        request.setStatus("COMPLETED");

        Task updatedTask = buildTask(1L, "Preparar documentación", TaskStatus.COMPLETED);
        TaskResponse updatedResponse = buildTaskResponse(updatedTask);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(taskMapper.toResponse(any(Task.class))).thenReturn(updatedResponse);

        TaskResponse result = taskService.updateTaskStatus(1L, request);

        ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepository).save(captor.capture());

        Task saved = captor.getValue();
        assertEquals(TaskStatus.COMPLETED, saved.getStatus());
        assertNotNull(saved.getUpdatedAt());
        assertEquals(updatedResponse, result);
    }

    @Test
    void updateTaskStatus_throwsWhenTaskNotFound() {
        UpdateStatusRequest request = new UpdateStatusRequest();
        request.setStatus("COMPLETED");

        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class,
                () -> taskService.updateTaskStatus(99L, request));
    }

    @Test
    void updateTaskStatus_acceptsLowercaseStatus() {
        UpdateStatusRequest request = new UpdateStatusRequest();
        request.setStatus("completed");

        Task updatedTask = buildTask(1L, "Preparar documentación", TaskStatus.COMPLETED);
        TaskResponse updatedResponse = buildTaskResponse(updatedTask);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(taskMapper.toResponse(any(Task.class))).thenReturn(updatedResponse);

        TaskResponse result = taskService.updateTaskStatus(1L, request);

        ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepository).save(captor.capture());

        Task saved = captor.getValue();
        assertEquals(TaskStatus.COMPLETED, saved.getStatus());
        assertEquals(updatedResponse, result);
    }

    @Test
    void updateTaskStatus_throwsWhenStatusInvalid() {
        UpdateStatusRequest request = new UpdateStatusRequest();
        request.setStatus("INVALID");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));

        assertThrows(InvalidStatusException.class,
                () -> taskService.updateTaskStatus(1L, request));
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void deleteTask_removesTaskWhenExists() {
        when(taskRepository.existsById(1L)).thenReturn(true);

        DeleteTaskResponse result = taskService.deleteTask(1L);

        verify(taskRepository).deleteById(1L);
        assertEquals("Task deleted successfully", result.getMessage());
    }

    @Test
    void deleteTask_throwsWhenNotFound() {
        when(taskRepository.existsById(99L)).thenReturn(false);

        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(99L));
        verify(taskRepository, never()).deleteById(99L);
    }

    private Task buildTask(Long id, String title, TaskStatus status) {
        Task task = new Task();
        task.setId(id);
        task.setTitle(title);
        task.setDescription("Generar documentación técnica");
        task.setStatus(status);
        task.setCreatedAt(LocalDateTime.of(2026, 5, 28, 10, 0));
        task.setUpdatedAt(LocalDateTime.of(2026, 5, 28, 10, 0));
        return task;
    }

    private TaskResponse buildTaskResponse(Task task) {
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus());
        response.setCreatedAt(task.getCreatedAt());
        response.setUpdatedAt(task.getUpdatedAt());
        return response;
    }
}
