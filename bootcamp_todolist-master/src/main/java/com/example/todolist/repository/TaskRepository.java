package com.example.todolist.repository;

import com.example.todolist.domain.task.Task;
import com.example.todolist.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByUserId(Long userId);
    void deleteTaskByUserId(Long id);
    void deleteAllTasksByUserId(Long id);

}