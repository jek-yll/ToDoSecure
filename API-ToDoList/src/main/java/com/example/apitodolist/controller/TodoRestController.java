package com.example.apitodolist.controller;

import com.example.apitodolist.model.Todo;
import com.example.apitodolist.service.ITodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/todoapi/v1")
public class TodoRestController {

    @Autowired
    private ITodoService todoService;

    @PostMapping("/todo") // http://localhost:8080/todoapi/v1/todo
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo){
        return ResponseEntity.ok(todoService.saveTodo(todo));
    }

    @GetMapping("/todos") // http://localhost:8080/todoapi/v1/todos
    public ResponseEntity<List<Todo>> getAllTodos(){
        return ResponseEntity.ok(todoService.getAllTodo());
    }

    @GetMapping("/todo/{id}") // http://localhost:8080/todoapi/v1/todo?id=$
    public ResponseEntity<Todo> getTodo(@PathVariable Long id){
        Optional<Todo> todo = todoService.getTodoById(id);
        return todo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/todos/{userId}") // http://localhost:8080/todoapi/v1/todos?userId=$
    public ResponseEntity<List<Todo>> getAllTodosByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(todoService.getAllTodoByUserId(userId));
    }

    @PutMapping ()// http://localhost:8080/todoapi/v1
    public ResponseEntity<Todo> updateTodo(@RequestBody Todo todo){
        return ResponseEntity.ok(todoService.updateTodo(todo));
    }

    @DeleteMapping("/todo/{id}") //http://localhost:8080/todoapi/v1/todo?id=$
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id){
        todoService.deleteTodo(id);
        return ResponseEntity.ok().build();
    }

}
