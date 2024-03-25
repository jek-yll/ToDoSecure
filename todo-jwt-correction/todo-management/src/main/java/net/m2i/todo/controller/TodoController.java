package net.m2i.todo.controller;

import lombok.AllArgsConstructor;
import net.m2i.todo.dto.TodoDto;
import net.m2i.todo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*") // Permet les requêtes cross-origin de n'importe quelle source.
@RestController // Marque la classe comme un contrôleur où chaque méthode renvoie un objet de domaine au lieu d'une vue.
@RequestMapping("api/todos/") // Mappage de base des requêtes pour ce contrôleur.
@AllArgsConstructor // Lombok: Génère le constructeur pour injecter `TodoService`.
public class TodoController {

    private TodoService todoService; // Service pour les opérations sur les tâches.

    // Ajoute une nouvelle tâche pour un utilisateur spécifique. Accès restreint à l'administrateur.
    @PostMapping("{id}")
    public ResponseEntity<TodoDto> addTodo(@PathVariable("id") Long idUser, @RequestBody TodoDto todoDto){
        TodoDto savedTodo = todoService.addTodo(idUser, todoDto);
        return new ResponseEntity<>(savedTodo, HttpStatus.CREATED);
    }

    // Récupère une tâche spécifique par son ID et l'ID de l'utilisateur. Accès restreint à l'administrateur.
    @GetMapping("{idUser}/todo/{id}")
    public ResponseEntity<TodoDto> getTodo(@PathVariable("idUser") Long idUser, @PathVariable("id") Long todoId){
        TodoDto todoDto = todoService.getTodo(idUser, todoId);
        return new ResponseEntity<>(todoDto, HttpStatus.OK);
    }

    // Récupère toutes les tâches d'un utilisateur spécifique. Accès restreint à l'administrateur.
    @GetMapping("{idUser}/allTodos")
    public ResponseEntity<List<TodoDto>> getAllTodosByUser(@PathVariable("idUser") Long idUser){
        List<TodoDto> todos = todoService.getAllTodos(idUser);
        return ResponseEntity.ok(todos);
    }

    // Récupère toutes les tâches. Accès restreint aux utilisateurs.
    @GetMapping("{idUser}/allTodosUser")
    public ResponseEntity<List<TodoDto>> getAllTodos(@PathVariable("idUser") Long idUser){
        List<TodoDto> todos = todoService.getTodos();
        return ResponseEntity.ok(todos);
    }

    // Met à jour une tâche spécifique. Accès restreint à l'administrateur.
    @PutMapping("{idUser}/update/{id}")
    public ResponseEntity<TodoDto> updateTodo(@PathVariable("idUser") Long idUser, @RequestBody TodoDto todoDto, @PathVariable("id") Long todoId){
        TodoDto updatedTodo = todoService.updateTodo(idUser, todoDto, todoId);
        return ResponseEntity.ok(updatedTodo);
    }

    // Supprime une tâche spécifique. Accès restreint à l'administrateur.
    @DeleteMapping("{idUser}/delete/{idTodo}")
    public ResponseEntity<String> deleteTodo(@PathVariable("idUser") Long idUser, @PathVariable("idTodo") Long todoId){
        todoService.deleteTodo(idUser, todoId);
        return ResponseEntity.ok("Todo deleted successfully!.");
    }

    // Marque une tâche spécifique comme complétée. Accès restreint à l'administrateur.
    @PatchMapping("{idUser}/complete/{id}")
    public ResponseEntity<TodoDto> completeTodo(@PathVariable("idUser") Long idUser, @PathVariable("id") Long todoId){
        TodoDto updatedTodo = todoService.completeTodo(idUser, todoId);
        return ResponseEntity.ok(updatedTodo);
    }

    // Cette annotation spécifie que l'accès à cette méthode est restreint aux utilisateurs ayant le rôle 'ADMIN'.
    // Elle emploie Spring Security pour évaluer l'expression avant d'exécuter la méthode.
    @PreAuthorize("hasAnyRole('ADMIN')")

    // Définit un point d'entrée de l'API pour une requête PATCH. La requête cible un utilisateur spécifique (idUser)
    // et une tâche spécifique (id) pour marquer cette tâche comme incomplète.
    @PatchMapping("{idUser}/in-complete/{id}")

    // ResponseEntity encapsule la réponse de l'API, incluant à la fois le corps de la réponse et le code d'état HTTP.
    // TodoDto est un objet de transfert de données (DTO) qui contient les données de la tâche.
    public ResponseEntity<TodoDto> inCompleteTodo(
            @PathVariable("idUser") Long idUser, // Extrait l'identifiant de l'utilisateur de l'URL de la requête.
            @PathVariable("id") Long todoId){ // Extrait l'identifiant de la tâche de l'URL de la requête.

        // Appelle la méthode inCompleteTodo du service, passant les identifiants de l'utilisateur et de la tâche,
        // puis reçoit le DTO de la tâche mise à jour.
        TodoDto updatedTodo = todoService.inCompleteTodo(idUser, todoId);

        // Renvoie le DTO de la tâche mise à jour avec un code d'état HTTP 200 (OK),
        // signifiant que la tâche a été marquée comme incomplète avec succès.
        return ResponseEntity.ok(updatedTodo);
    }


}
