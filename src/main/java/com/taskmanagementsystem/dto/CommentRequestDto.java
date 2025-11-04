package com.taskmanagementsystem.dto;

import lombok.Data;

@Data
public class CommentRequestDto {
    private String text;
    private Long userId;
}