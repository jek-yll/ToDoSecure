package net.m2i.todo.service.impl;

import net.m2i.todo.entity.Role;
import net.m2i.todo.repository.RoleRepository;
import net.m2i.todo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {



    @Autowired
    RoleRepository roleRepository;

    @Override
    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }
}
