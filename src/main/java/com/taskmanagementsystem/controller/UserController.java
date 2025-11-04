package com.taskmanagementsystem.controller;

import com.taskmanagementsystem.dto.UserDto;
import com.taskmanagementsystem.dto.TaskResponseDto;
import com.taskmanagementsystem.service.TaskService;
import com.taskmanagementsystem.entity.User;
import com.taskmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtos = userService.getAllUsersAsDto();
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/{userId}/tasks")
    public ResponseEntity<List<TaskResponseDto>> getUserTasks(@PathVariable Long userId) {
        List<TaskResponseDto> tasks = taskService.getTasksByUserId(userId);
        return ResponseEntity.ok(tasks);
    }
}
