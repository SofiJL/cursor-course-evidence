package com.example.taskflow.controller;

import com.example.taskflow.dto.CreateTaskRequest;
import com.example.taskflow.dto.DeleteTaskResponse;
import com.example.taskflow.dto.TaskResponse;
import com.example.taskflow.dto.TaskSummaryResponse;
import com.example.taskflow.dto.UpdateStatusRequest;
import com.example.taskflow.exception.GlobalExceptionHandler;
import com.example.taskflow.exception.InvalidStatusException;
import com.example.taskflow.exception.TaskNotFoundException;
import com.example.taskflow.model.TaskStatus;
import com.example.taskflow.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@Import(GlobalExceptionHandler.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TaskService taskService;

    @Test
    void createTask_returns201WithFullContract() throws Exception {
        TaskResponse response = buildTaskResponse(1L, TaskStatus.PENDING);

        when(taskService.createTask(any(CreateTaskRequest.class))).thenReturn(response);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "title": "Preparar documentación",
                                  "description": "Generar documentación técnica"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Preparar documentación"))
                .andExpect(jsonPath("$.description").value("Generar documentación técnica"))
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andExpect(jsonPath("$.created_at").exists())
                .andExpect(jsonPath("$.updated_at").exists());
    }

    @Test
    void createTask_returns400WhenTitleMissing() throws Exception {
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "description": "Sin título"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Title is required"));
    }

    @Test
    void getAllTasks_returnsSummaryList() throws Exception {
        TaskSummaryResponse summary = new TaskSummaryResponse();
        summary.setId(1L);
        summary.setTitle("Preparar documentación");
        summary.setStatus(TaskStatus.PENDING);

        when(taskService.getAllTasks()).thenReturn(List.of(summary));

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Preparar documentación"))
                .andExpect(jsonPath("$[0].status").value("PENDING"))
                .andExpect(jsonPath("$[0].description").doesNotExist())
                .andExpect(jsonPath("$[0].created_at").doesNotExist());
    }

    @Test
    void getTaskById_returnsFullDetail() throws Exception {
        TaskResponse response = buildTaskResponse(1L, TaskStatus.PENDING);

        when(taskService.getTaskById(1L)).thenReturn(response);

        mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Preparar documentación"))
                .andExpect(jsonPath("$.description").value("Generar documentación técnica"))
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andExpect(jsonPath("$.created_at").value("2026-05-28T10:00:00"))
                .andExpect(jsonPath("$.updated_at").value("2026-05-28T10:00:00"));
    }

    @Test
    void getTaskById_returns404WhenNotFound() throws Exception {
        when(taskService.getTaskById(99L)).thenThrow(new TaskNotFoundException(99L));

        mockMvc.perform(get("/tasks/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Task not found with id: 99"));
    }

    @Test
    void getTaskById_returns400WhenIdIsNotNumeric() throws Exception {
        mockMvc.perform(get("/tasks/abc"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid task id: abc"));
    }

    @Test
    void updateTaskStatus_returnsUpdatedTask() throws Exception {
        TaskResponse response = buildTaskResponse(1L, TaskStatus.COMPLETED);
        response.setUpdatedAt(LocalDateTime.of(2026, 5, 28, 12, 30));

        when(taskService.updateTaskStatus(eq(1L), any(UpdateStatusRequest.class)))
                .thenReturn(response);

        mockMvc.perform(put("/tasks/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "status": "COMPLETED"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("COMPLETED"))
                .andExpect(jsonPath("$.updated_at").value("2026-05-28T12:30:00"));
    }

    @Test
    void updateTaskStatus_returns400WhenStatusInvalid() throws Exception {
        when(taskService.updateTaskStatus(eq(1L), any(UpdateStatusRequest.class)))
                .thenThrow(new InvalidStatusException("INVALID"));

        mockMvc.perform(put("/tasks/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "status": "INVALID"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid status: INVALID"));
    }

    @Test
    void deleteTask_returnsSuccessMessage() throws Exception {
        when(taskService.deleteTask(1L))
                .thenReturn(new DeleteTaskResponse("Task deleted successfully"));

        mockMvc.perform(delete("/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Task deleted successfully"));
    }

    @Test
    void deleteTask_returns404WhenNotFound() throws Exception {
        doThrow(new TaskNotFoundException(99L)).when(taskService).deleteTask(99L);

        mockMvc.perform(delete("/tasks/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Task not found with id: 99"));
    }

    private TaskResponse buildTaskResponse(Long id, TaskStatus status) {
        TaskResponse response = new TaskResponse();
        response.setId(id);
        response.setTitle("Preparar documentación");
        response.setDescription("Generar documentación técnica");
        response.setStatus(status);
        response.setCreatedAt(LocalDateTime.of(2026, 5, 28, 10, 0));
        response.setUpdatedAt(LocalDateTime.of(2026, 5, 28, 10, 0));
        return response;
    }
}
