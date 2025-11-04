package com.taskmanagementsystem.repository;


import com.taskmanagementsystem.entity.Task;
import com.taskmanagementsystem.entity.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findByStatus(TaskStatus status, Pageable pageable);
    Page<Task> findByTags_Name(String tagName, Pageable pageable);
    Page<Task> findByStatusAndTags_Name(TaskStatus status, String tagName, Pageable pageable);
}