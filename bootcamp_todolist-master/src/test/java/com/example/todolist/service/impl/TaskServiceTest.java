package com.example.todolist.service.impl;

import com.example.todolist.domain.exception.ResourceNotFoundException;
import com.example.todolist.domain.task.Status;
import com.example.todolist.domain.task.Task;
import com.example.todolist.domain.user.User;
import com.example.todolist.repository.TaskRepository;
import com.example.todolist.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private User user;
    private Task task;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password");

        task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setDescription("Test task description");
        task.setStatus(Status.TODO);
        task.setDateTime(LocalDateTime.now());
        task.setUser(user);
    }

    @Test
    public void testCreateTask() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task createdTask = taskService.create(task, user.getId());

        assertThat(createdTask).isNotNull();
        assertThat(createdTask.getUser()).isEqualTo(user);
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    public void testGetById() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        Task found = taskService.getById(1L);
        assertThat(found).isEqualTo(task);
    }

    @Test
    public void testGetById_NotFound() {
        when(taskRepository.findById(2L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> taskService.getById(2L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void testGetAllByUserId() {
        when(taskRepository.findAllByUserId(1L)).thenReturn(List.of(task));
        List<Task> tasks = taskService.getAllByUserId(1L);
        assertThat(tasks).hasSize(1);
    }

    @Test
    public void testUpdateTask() {
        Task updatedTask = new Task();
        updatedTask.setId(1L);
        updatedTask.setTitle("Updated");
        updatedTask.setDescription("Updated Desc");
        updatedTask.setStatus(Status.DONE);
        updatedTask.setDateTime(LocalDateTime.now());

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        Task result = taskService.update(updatedTask);
        assertThat(result.getTitle()).isEqualTo("Updated");
    }

    @Test
    public void testDeleteById() {
        doNothing().when(taskRepository).deleteById(1L);
        taskService.deleteById(1L);
        verify(taskRepository).deleteById(1L);
    }
}
