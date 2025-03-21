package com.example.todolist.web.controller;

import com.example.todolist.domain.task.Task;
import com.example.todolist.service.TaskService;
import com.example.todolist.web.dto.task.TaskDto;
import com.example.todolist.web.dto.validation.OnUpdate;
import com.example.todolist.web.mappers.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import com.example.todolist.web.dto.validation.OnCreate;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Validated
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;


    @GetMapping("/{id}")
    public TaskDto getById(@PathVariable Long id){
        Task task = taskService.getById(id);
        return taskMapper.toDto(task);
    }



    @DeleteMapping("/{id}")
    public void deleteByTitle(@PathVariable Long id){
        taskService.deleteById(id);
    }



    @PutMapping
    public TaskDto update(@Validated(OnUpdate.class) @RequestBody TaskDto taskDto){
        Task task = taskMapper.toEntity(taskDto);
        Task updatedTask = taskService.update(task);
        return taskMapper.toDto(updatedTask);
    }

    @PostMapping
    public TaskDto create(@Validated(OnCreate.class) @RequestBody TaskDto taskDto) {
        Task task = taskMapper.toEntity(taskDto);
        Task created = taskService.create(task, taskDto.getUserId());
        return taskMapper.toDto(created);
    }



}
