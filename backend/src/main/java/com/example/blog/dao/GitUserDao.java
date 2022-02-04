package com.example.blog.dao;

import com.example.blog.entity.GitUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GitUserDao extends JpaRepository<GitUser,Long> {
}
