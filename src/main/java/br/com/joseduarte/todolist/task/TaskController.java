package br.com.joseduarte.todolist.task;


import br.com.joseduarte.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody TaskModel newTask, HttpServletRequest request) {
        newTask.setIdUser((UUID) request.getAttribute("idUser"));

        if (LocalDateTime.now().isAfter(newTask.getStartAt()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data de inicio deve ser maior que a data atual");

        if (LocalDateTime.now().isAfter(newTask.getEndAt()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data final deve ser maior que a data atual");

        if (newTask.getStartAt().isAfter(newTask.getEndAt()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data de inicio deve ser menor que a data de conclusao");

        return ResponseEntity.status(HttpStatus.CREATED).body(this.taskRepository.save(newTask));

    }

    @GetMapping("/")
    public ResponseEntity<?> list(HttpServletRequest request) {
        ArrayList<TaskModel> tasks = this.taskRepository.findAllByIdUser((UUID) request.getAttribute("idUser"));
        return ResponseEntity.status(HttpStatus.OK).body(tasks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id) {

        TaskModel task = this.taskRepository.findById(id).orElse(null);

        if (Objects.isNull(task)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("task not found");

        if(!task.getIdUser().equals(request.getAttribute("idUser"))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Task nao pertence ao usuario");
        }

        Utils.copyNonNullProperties(taskModel, task);

        TaskModel updatedTask = this.taskRepository.save(task);

        return ResponseEntity.status(HttpStatus.OK).body(updatedTask);
    }
}
