package com.taskmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskmanagementsystem.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {


}
