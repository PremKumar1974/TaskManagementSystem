package com.taskmanagementsystem.service;

import com.taskmanagementsystem.entity.Comment;
import com.taskmanagementsystem.entity.Task;
import com.taskmanagementsystem.dto.CommentResponseDto;
import com.taskmanagementsystem.dto.CommentUserDto;
import com.taskmanagementsystem.entity.User;
import com.taskmanagementsystem.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Transactional
    public CommentResponseDto addCommentToTask(Long taskId, Long userId, Comment commentRequest) {
        Task task = taskService.getTaskById(taskId);
        User user = userService.getUserById(userId);


        commentRequest.setTask(task);
        commentRequest.setUser(user);

        Comment savedComment = commentRepository.save(commentRequest);
        return convertToDto(savedComment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getCommentsByTaskId(Long taskId) {
        return commentRepository.findByTaskId(taskId)
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    private CommentResponseDto convertToDto(Comment comment) {
        CommentResponseDto dto = new CommentResponseDto();
        dto.setId(comment.getId());
        dto.setText(comment.getText());
        dto.setCreatedAt(comment.getCreatedAt());

        CommentUserDto userDto = new CommentUserDto();
        userDto.setId(comment.getUser().getId());
        userDto.setName(comment.getUser().getName());
        dto.setUser(userDto);

        return dto;
    }
}
