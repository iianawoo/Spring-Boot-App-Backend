package com.example.todolist.integration;

import com.example.todolist.domain.task.Status;
import com.example.todolist.domain.task.Task;
import com.example.todolist.domain.user.Role;
import com.example.todolist.domain.user.User;
import com.example.todolist.repository.TaskRepository;
import com.example.todolist.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        taskRepository.deleteAll();
        userRepository.deleteAll();

        // ✅ Добавляем пользователя заранее
        User user = new User();
        user.setId(1L); // Важно: задать ID вручную, если он нужен в JSON
        user.setUsername("testuser");
        user.setPassword("123"); // пароль тут не хешируется, потому что мы не логинимся
        user.setName("Test User");
        user.setRole(Role.ROLE_USER);
        userRepository.saveAndFlush(user);
    }

    @Test
    public void testCreateTask() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String taskJson = """
        {
          "title": "New Task",
          "description": "Test task description",
          "status": "TODO",
          "userId": 1
        }
        """;

        HttpEntity<String> request = new HttpEntity<>(taskJson, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "/api/v1/tasks",
                HttpMethod.POST,
                request,
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
