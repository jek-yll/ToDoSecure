package net.m2i.todo.controller;

import lombok.AllArgsConstructor;
import net.m2i.todo.entity.Role;
import net.m2i.todo.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/roles/")
@AllArgsConstructor
public class RoleController {


    private RoleService roleService;


    @GetMapping("allRoles")
    public ResponseEntity<List<Role>> getAllTodos(){
        List<Role> roles = roleService.getAllRole();
        return ResponseEntity.ok(roles);
    }


}
