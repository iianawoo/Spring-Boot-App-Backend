package com.example.todolist.service;

import com.example.todolist.domain.task.Task;
import com.example.todolist.domain.user.User;

import java.util.List;

public interface TaskService {

    void assignToUserById(Long taskId, Long userId);
    Task getById(Long id);
    List<Task> getAllByUserId(Long userId);
    Task update(Task task);
    Task create(Task task, Long userId);
    void deleteById(Long id);


    List<Task> getAll();
}
