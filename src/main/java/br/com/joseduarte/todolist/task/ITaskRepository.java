package br.com.joseduarte.todolist.task;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.UUID;

public interface ITaskRepository extends JpaRepository<TaskModel, UUID> {
    ArrayList<TaskModel> findAllByIdUser(UUID idUser);
}
