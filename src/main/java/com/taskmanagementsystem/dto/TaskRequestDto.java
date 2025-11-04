package com.taskmanagementsystem.dto;

import com.taskmanagementsystem.entity.TaskStatus;
import lombok.Data;

@Data
public class TaskRequestDto {
    private String title;
    private String description;
    private TaskStatus status;
    private Long userId;
}