package com.taskmanagementsystem.controller;

import com.taskmanagementsystem.dto.MessageResponseDto;
import com.taskmanagementsystem.dto.TaskRequestDto;
import com.taskmanagementsystem.dto.TaskResponseDto;
import com.taskmanagementsystem.dto.TaskTagResponseDto;
import com.taskmanagementsystem.entity.Task;
import com.taskmanagementsystem.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskRequestDto taskRequest) {
        Task createdTask = taskService.createTask(taskRequest);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<TaskResponseDto>> getAllTasks(
            @RequestParam(required = false) String status,
            @RequestParam(required = false, name = "tag") String tagName,
            Pageable pageable) {
        Page<TaskResponseDto> tasks = taskService.getAllTasks(status, tagName, pageable);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskResponseDto>> getTasksByUserId(@PathVariable Long userId) {
        List<TaskResponseDto> tasks = taskService.getTasksByUserId(userId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable Long id) {
        TaskResponseDto taskDto = taskService.getTaskByIdAsDto(id);
        return ResponseEntity.ok(taskDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDto> updateTask(
            @PathVariable Long id,
            @RequestBody Task taskDetails) {
        TaskResponseDto updatedTask = taskService.updateTaskAsDto(id, taskDetails);
        return ResponseEntity.ok(updatedTask);
    }

    @PostMapping("/{taskId}/tags/{tagId}")
    public ResponseEntity<TaskTagResponseDto> assignTagToTask(
            @PathVariable Long taskId,
            @PathVariable Long tagId) {
        TaskTagResponseDto updatedTask = taskService.assignTagToTask(taskId, tagId);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{taskId}/tags/{tagId}")
    public ResponseEntity<MessageResponseDto> removeTagFromTask(
            @PathVariable Long taskId,
            @PathVariable Long tagId) {
        MessageResponseDto response = taskService.removeTagFromTask(taskId, tagId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDto> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok(new MessageResponseDto("Task deleted successfully"));
    }
}
