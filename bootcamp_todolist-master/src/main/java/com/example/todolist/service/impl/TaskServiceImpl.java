package com.example.todolist.service.impl;

import com.example.todolist.domain.exception.ResourceNotFoundException;
import com.example.todolist.domain.task.Status;
import com.example.todolist.domain.task.Task;
import com.example.todolist.domain.user.User;
import com.example.todolist.repository.TaskRepository;
import com.example.todolist.repository.UserRepository;
import com.example.todolist.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    public void assignToUserById(Long taskId, Long userId) {

    }
    @Override
    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Task getById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("task not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> getAllByUserId(Long userId) {
        return taskRepository.findAllByUserId(userId);
    }

    @Override
    @Transactional
    public Task update(Task task) {
        Task updateTask = getById(task.getId());
        if(task.getStatus() == null){
            updateTask.setStatus(Status.TODO);
        }
        else{
            updateTask.setStatus(task.getStatus());
        }
        updateTask.setUser(task.getUser());
        updateTask.setTitle(task.getTitle());
        updateTask.setDescription(task.getDescription());
        updateTask.setDateTime(task.getDateTime());
        taskRepository.save(updateTask);
        return updateTask;
    }

    @Override
    @Transactional
    public Task create(Task task, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("not found user"));
        task.setUser(user);
        task.setStatus(Status.TODO);
        taskRepository.save(task);
        return task;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

}
