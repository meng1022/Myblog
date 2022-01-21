package com.example.blog.service;

import java.util.List;

import com.example.blog.dao.ModuleDao;
import com.example.blog.entity.Module;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ModuleService {
    @Resource
    ModuleDao moduleDao;

    public List<Module> getmodules(){
        return moduleDao.findAll();
    }

    public void createmodule(String modulename){
        Module module = new Module();
        module.setModulename(modulename);
        moduleDao.save(module);
    }
}
