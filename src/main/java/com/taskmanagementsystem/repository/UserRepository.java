package com.taskmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskmanagementsystem.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
