package com.example.apitodolist.service;

import com.example.apitodolist.model.Todo;

import java.util.List;
import java.util.Optional;

public interface ITodoService {

    Todo saveTodo(Todo t);

    List<Todo> getAllTodo();

    Optional<Todo> getTodoById(Long id);

    Todo updateTodo(Todo todo);

    List<Todo> getAllTodoByUserId(Long userId);

    void deleteTodo(Long id);
}
