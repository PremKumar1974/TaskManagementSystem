package com.taskmanagementsystem.dto;

import com.taskmanagementsystem.entity.Tag;
import lombok.Data;

import java.util.Set;

@Data
public class TaskTagResponseDto {
    private Long id;
    private String title;
    private Set<Tag> tags;
}