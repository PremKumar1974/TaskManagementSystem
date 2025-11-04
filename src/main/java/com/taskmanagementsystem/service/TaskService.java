package com.taskmanagementsystem.service;

import com.taskmanagementsystem.dto.MessageResponseDto;
import com.taskmanagementsystem.dto.TaskRequestDto;
import com.taskmanagementsystem.dto.TaskResponseDto;
import com.taskmanagementsystem.dto.TaskTagResponseDto;
import com.taskmanagementsystem.dto.UserDto;
import com.taskmanagementsystem.entity.Tag;
import com.taskmanagementsystem.entity.Task;
import com.taskmanagementsystem.entity.TaskStatus;
import com.taskmanagementsystem.entity.User;
import com.taskmanagementsystem.exception.ResourceNotFoundException;
import com.taskmanagementsystem.repository.TagRepository;
import com.taskmanagementsystem.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final TagRepository tagRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserService userService, TagRepository tagRepository) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.tagRepository = tagRepository;
    }

    @Transactional
    public Task createTask(TaskRequestDto requestDto) {
        User user = userService.getUserById(requestDto.getUserId());

        Task newTask = new Task();
        newTask.setTitle(requestDto.getTitle());
        newTask.setDescription(requestDto.getDescription());
        newTask.setUser(user);

        if (requestDto.getStatus() == null) {
            newTask.setStatus(TaskStatus.TODO);
        } else {
            newTask.setStatus(requestDto.getStatus());
        }

        return taskRepository.save(newTask);
    }

    @Transactional(readOnly = true)
    public Page<TaskResponseDto> getAllTasks(String status, String tagName, Pageable pageable) {
        boolean hasStatus = status != null && !status.isBlank();
        boolean hasTag = tagName != null && !tagName.isBlank();

        Page<Task> tasks;
        if (hasStatus && hasTag) {
            try {
                TaskStatus taskStatus = TaskStatus.valueOf(status.toUpperCase());
                tasks = taskRepository.findByStatusAndTags_Name(taskStatus, tagName, pageable);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid status value: " + status);
            }
        } else if (hasStatus) {
            tasks = taskRepository.findByStatus(TaskStatus.valueOf(status.toUpperCase()), pageable);
        } else if (hasTag) {
            tasks = taskRepository.findByTags_Name(tagName, pageable);
        } else {
            tasks = taskRepository.findAll(pageable);
        }

        return tasks.map(this::convertToDto);
    }

    @Transactional(readOnly = true)
    public List<TaskResponseDto> getTasksByUserId(Long userId) {
        User user = userService.getUserById(userId);
        return user.getTasks().stream().map(this::convertToDto).toList();
    }

    @Transactional(readOnly = true)
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public TaskResponseDto getTaskByIdAsDto(Long id) {
        Task task = getTaskById(id);
        return convertToDto(task);
    }

    @Transactional
    public TaskResponseDto updateTaskAsDto(Long id, Task taskRequest) {
        Task task = getTaskById(id);
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setStatus(taskRequest.getStatus());
        Task updatedTask = taskRepository.save(task);
        return convertToDto(updatedTask);
    }

    @Transactional
    public void deleteTask(Long id) {
        Task task = getTaskById(id);
        var tagsToCheck = new HashSet<>(task.getTags());

        taskRepository.delete(task);

        for (Tag tag : tagsToCheck) {
            if (tagRepository.findById(tag.getId()).get().getTasks().isEmpty()) {
                tagRepository.delete(tag);
            }
        }
    }

    @Transactional
    public TaskTagResponseDto assignTagToTask(Long taskId, Long tagId) {
        Task task = getTaskById(taskId);
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found with id: " + tagId));

        task.getTags().add(tag);
        Task savedTask = taskRepository.save(task);

        TaskTagResponseDto dto = new TaskTagResponseDto();
        dto.setId(savedTask.getId());
        dto.setTitle(savedTask.getTitle());
        dto.setTags(savedTask.getTags());
        return dto;
    }

    @Transactional
    public MessageResponseDto removeTagFromTask(Long taskId, Long tagId) {
        Task task = getTaskById(taskId);
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found with id: " + tagId));

        if (task.getTags().remove(tag)) {
            taskRepository.save(task);
            return new MessageResponseDto(
                    String.format("The tag '%s' in task %d has been deleted successfully.", tag.getName(), taskId));
        }

        return new MessageResponseDto(String.format("Tag '%s' was not found on task %d.", tag.getName(), taskId));
    }

    private TaskResponseDto convertToDto(Task task) {
        TaskResponseDto taskDto = new TaskResponseDto();
        taskDto.setId(task.getId());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setStatus(task.getStatus());
        taskDto.setTags(task.getTags());
        taskDto.setCreatedAt(task.getCreatedAt());
        taskDto.setUpdatedAt(task.getUpdatedAt());

        if (task.getUser() != null) {
            UserDto userDto = new UserDto();
            userDto.setId(task.getUser().getId());
            userDto.setName(task.getUser().getName());
            userDto.setEmail(task.getUser().getEmail());
            taskDto.setUser(userDto);
        }
        return taskDto;
    }
}
