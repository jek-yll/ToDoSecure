package net.m2i.todo.service.impl;

import lombok.AllArgsConstructor;
import net.m2i.todo.dto.TodoDto;
import net.m2i.todo.entity.Todo;
import net.m2i.todo.entity.User;
import net.m2i.todo.exception.ResourceNotFoundException;
import net.m2i.todo.repository.TodoRepository;
import net.m2i.todo.repository.UserRepository;
import net.m2i.todo.service.TodoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {

    private TodoRepository todoRepository;

    private UserRepository userRepository;

    private ModelMapper modelMapper;

    @Override
    public TodoDto addTodo(Long idUser, TodoDto todoDto) {
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + idUser));

        Todo todo = modelMapper.map(todoDto, Todo.class);
        todo.setUser(user); // Associez le Todo à l'utilisateur

        Todo savedTodo = todoRepository.save(todo);
        return modelMapper.map(savedTodo, TodoDto.class);
    }





    @Override
    public TodoDto getTodo(Long idUser, Long id) {
        Todo todo = todoRepository.findByIdAndUserId(id, idUser)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id + " for user id: " + idUser));

        return modelMapper.map(todo, TodoDto.class);
    }


    @Override
    public List<TodoDto> getAllTodos(Long idUser) {
        List<Todo> todos = todoRepository.findAllByUserId(idUser);

        return todos.stream().map(todo -> modelMapper.map(todo, TodoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TodoDto> getTodos() {
        List<Todo> todoList = todoRepository.findAll();
        return todoList.stream().map(todo -> modelMapper.map(todo, TodoDto.class)).collect(Collectors.toList());
    }


    @Override
    public TodoDto updateTodo(Long idUser, TodoDto todoDto, Long id) {
        // Chercher le Todo en s'assurant qu'il appartient à l'utilisateur spécifié
        Todo todo = todoRepository.findByIdAndUserId(id, idUser)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id + " or does not belong to user with id: " + idUser));

        // Mettre à jour le Todo avec les informations fournies dans todoDto
        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCompleted(todoDto.isCompleted());

        // Sauvegarder les modifications
        Todo updatedTodo = todoRepository.save(todo);

        // Mapper le Todo mis à jour en TodoDto et le retourner
        return modelMapper.map(updatedTodo, TodoDto.class);
    }


    @Override
    public void deleteTodo(Long idUser, Long id) {
        Todo todo = todoRepository.findByIdAndUserId(id, idUser)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));

        todoRepository.deleteById(id);
    }



    @Override
    public TodoDto completeTodo(Long idUser, Long id) {
        // Trouver le Todo en s'assurant qu'il appartient à l'utilisateur
        Todo todo = todoRepository.findByIdAndUserId(id, idUser)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id + " or does not belong to user with id: " + idUser));

        // Marquer le Todo comme complété
        todo.setCompleted(Boolean.TRUE);

        // Sauvegarder les changements
        Todo updatedTodo = todoRepository.save(todo);

        // Mapper et retourner le Todo mis à jour
        return modelMapper.map(updatedTodo, TodoDto.class);
    }


    @Override
    public TodoDto inCompleteTodo(Long idUser, Long id) {
        // Trouver le Todo en s'assurant qu'il appartient à l'utilisateur
        Todo todo = todoRepository.findByIdAndUserId(id, idUser)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id + " or does not belong to user with id: " + idUser));

        // Marquer le Todo comme non complété
        todo.setCompleted(Boolean.FALSE);

        // Sauvegarder les changements
        Todo updatedTodo = todoRepository.save(todo);

        // Mapper et retourner le Todo mis à jour
        return modelMapper.map(updatedTodo, TodoDto.class);
    }

}
