package com.taskmanagementsystem.dto;

import com.taskmanagementsystem.entity.Tag;
import com.taskmanagementsystem.entity.TaskStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class TaskResponseDto {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private UserDto user;
    private Set<Tag> tags;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}