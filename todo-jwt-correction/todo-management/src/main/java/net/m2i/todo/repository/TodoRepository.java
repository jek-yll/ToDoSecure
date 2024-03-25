package net.m2i.todo.repository;

import net.m2i.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;


public interface TodoRepository extends JpaRepository<Todo, Long> {

    Optional<Todo> findByIdAndUserId(Long id, Long userId);

    List<Todo> findAllByUserId(Long userId);



}
