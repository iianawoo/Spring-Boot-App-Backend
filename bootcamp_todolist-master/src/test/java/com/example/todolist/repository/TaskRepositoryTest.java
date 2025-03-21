package com.example.todolist.repository;

import com.example.todolist.domain.task.Status;
import com.example.todolist.domain.task.Task;
import com.example.todolist.domain.user.Role;
import com.example.todolist.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository; // Должен быть тоже создан для доступа к данным пользователей

    private User user;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setRole(Role.ROLE_USER); // или другой подходящий статус
        userRepository.save(user);

    }

    @Test
    public void testCreateTask() {
        // Создаем задачу для теста
        Task task = new Task();
        task.setTitle("Test Task");
        task.setDescription("This is a test task.");
        task.setStatus(Status.TODO);
        task.setDateTime(LocalDateTime.now());
        task.setUser(user); // Связываем задачу с пользователем

        // Сохраняем задачу в репозитории
        Task savedTask = taskRepository.save(task);

        // Проверяем, что задача была сохранена
        assertThat(savedTask.getId()).isNotNull();
        assertThat(savedTask.getTitle()).isEqualTo("Test Task");
        assertThat(savedTask.getDescription()).isEqualTo("This is a test task.");
        assertThat(savedTask.getStatus()).isEqualTo(Status.TODO);
        assertThat(savedTask.getUser()).isEqualTo(user);
    }

    @Test
    public void testFindAllByUserId() {
        // Создаем задачи для теста
        Task task1 = new Task();
        task1.setTitle("Task 1");
        task1.setDescription("First task.");
        task1.setStatus(Status.TODO);
        task1.setDateTime(LocalDateTime.now());
        task1.setUser(user);

        Task task2 = new Task();
        task2.setTitle("Task 2");
        task2.setDescription("Second task.");
        task2.setStatus(Status.IN_PROGRESS);
        task2.setDateTime(LocalDateTime.now());
        task2.setUser(user);

        taskRepository.save(task1);
        taskRepository.save(task2);

        // Получаем все задачи для пользователя
        List<Task> tasks = taskRepository.findAllByUserId(user.getId());

        // Проверяем, что задачи найдены
        assertThat(tasks).hasSize(2);
        assertThat(tasks).extracting(Task::getUser).containsOnly(user);
    }

    @Test
    public void testDeleteTaskByUserId() {
        // Создаем задачу
        Task task = new Task();
        task.setTitle("Task to delete");
        task.setDescription("This task will be deleted.");
        task.setStatus(Status.TODO);
        task.setDateTime(LocalDateTime.now());
        task.setUser(user);

        task = taskRepository.save(task);

        // Удаляем задачу по ID пользователя
        taskRepository.deleteTaskByUserId(user.getId());

        // Проверяем, что задача была удалена
        List<Task> tasks = taskRepository.findAllByUserId(user.getId());
        assertThat(tasks).isEmpty();
    }

    @Test
    public void testDeleteAllTasksByUserId() {
        // Создаем и сохраняем задачи
        Task task1 = new Task();
        task1.setTitle("Task 1");
        task1.setDescription("Task to be deleted.");
        task1.setStatus(Status.TODO);
        task1.setDateTime(LocalDateTime.now());
        task1.setUser(user);
        taskRepository.save(task1);

        Task task2 = new Task();
        task2.setTitle("Task 2");
        task2.setDescription("Another task to be deleted.");
        task2.setStatus(Status.TODO);
        task2.setDateTime(LocalDateTime.now());
        task2.setUser(user);
        taskRepository.save(task2);

        // Удаляем все задачи пользователя
        taskRepository.deleteAllTasksByUserId(user.getId());

        // Проверяем, что все задачи удалены
        List<Task> tasks = taskRepository.findAllByUserId(user.getId());
        assertThat(tasks).isEmpty();
    }
}
