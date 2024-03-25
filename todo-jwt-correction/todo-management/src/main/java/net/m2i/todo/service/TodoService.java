package net.m2i.todo.service;

import net.m2i.todo.dto.TodoDto;

import java.util.List;

public interface TodoService {

    TodoDto addTodo(Long idUser,TodoDto todoDto);

    TodoDto getTodo(Long idUser,Long id);

    List<TodoDto> getAllTodos(Long idUser);

    List<TodoDto> getTodos();

    TodoDto updateTodo(Long idUser,TodoDto todoDto, Long id);

    void deleteTodo( Long idUser,Long id);

    TodoDto completeTodo( Long idUser,Long id);

    TodoDto inCompleteTodo(Long idUser,Long id);
}
