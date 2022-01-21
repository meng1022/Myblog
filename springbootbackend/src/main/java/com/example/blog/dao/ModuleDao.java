package com.example.blog.dao;

import com.example.blog.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleDao extends JpaRepository<Module,Long> {

}
