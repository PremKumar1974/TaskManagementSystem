package com.taskmanagementsystem.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CommentResponseDto {
    private Long id;
    private String text;
    private CommentUserDto user;
    private LocalDateTime createdAt;

}