package com.example.todolist.web.controller;

import com.example.todolist.domain.task.Task;
import com.example.todolist.domain.user.User;
import com.example.todolist.service.TaskService;
import com.example.todolist.service.UserService;
import com.example.todolist.web.dto.task.TaskDto;
import com.example.todolist.web.dto.user.UserDto;
import com.example.todolist.web.dto.validation.OnCreate;
import com.example.todolist.web.dto.validation.OnUpdate;
import com.example.todolist.web.mappers.TaskMapper;
import com.example.todolist.web.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;
    private final TaskService taskService;
    private final UserMapper userMapper;
    private final TaskMapper taskMapper;



    @PutMapping
    @PreAuthorize("@SecurityExpression.canAccessUser(#userDto.id)")
    public UserDto update(@Validated(OnUpdate.class) @RequestBody UserDto userDto){
        User user = userMapper.toEntity(userDto);
        User updatedUser = userService.update(user);
        return userMapper.toDto(updatedUser);
    }



    @GetMapping("/{id}")
    @PreAuthorize("@SecurityExpression.canAccessUser(#id)")
    public UserDto getById(@PathVariable Long id){
        User user = userService.getById(id);
        return userMapper.toDto(user);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("@SecurityExpression.canAccessUser(#id)")
    public void deleteById(@PathVariable Long id){
        userService.deleteById(id);
    }



    @GetMapping("/{id}/tasks")
    @PreAuthorize("@SecurityExpression.canAccessTask(#id)")
    public List<TaskDto> getTasksByUserId(@PathVariable Long id){
        List<Task> tasks = taskService.getAllByUserId(id);
        return taskMapper.toDto(tasks);
    }


    @PostMapping("/{id}/tasks")
    @PreAuthorize("@SecurityExpression.canAccessUser(#id)")
    public TaskDto createTask(@PathVariable Long id,
                              @Validated(OnCreate.class) @RequestBody TaskDto taskDto){
        Task task = taskMapper.toEntity(taskDto);
        Task createdTask = taskService.create(task, id);
        return taskMapper.toDto(createdTask);
    }
}

