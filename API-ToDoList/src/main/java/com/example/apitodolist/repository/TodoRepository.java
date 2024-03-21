package com.example.apitodolist.repository;

import com.example.apitodolist.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> getAllByUserId(Long useId);
}
