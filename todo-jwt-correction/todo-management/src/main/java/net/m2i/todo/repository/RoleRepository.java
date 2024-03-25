package net.m2i.todo.repository;

import net.m2i.todo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);



    @Query("SELECT r FROM Role r WHERE r.id = :id")
    Role findRoleBy(Long id);
}
