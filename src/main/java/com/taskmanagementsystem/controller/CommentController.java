package com.taskmanagementsystem.controller;

import com.taskmanagementsystem.entity.Comment;
import com.taskmanagementsystem.dto.CommentResponseDto;
import com.taskmanagementsystem.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks/{taskId}/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @PostMapping
    public ResponseEntity<CommentResponseDto> addCommentToTask(@PathVariable Long taskId, @RequestParam Long userId, @RequestBody Comment comment) {
        CommentResponseDto createdComment = commentService.addCommentToTask(taskId, userId, comment);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getCommentsByTask(@PathVariable Long taskId) {
        List<CommentResponseDto> comments = commentService.getCommentsByTaskId(taskId);
        return ResponseEntity.ok(comments);
    }
}