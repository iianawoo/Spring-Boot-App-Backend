package com.example.todolist.web.dto.task;

import com.example.todolist.domain.task.Status;
import com.example.todolist.web.dto.validation.OnCreate;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class TaskDtoTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidTaskDto() {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(1L);
        taskDto.setTitle("Test Task");
        taskDto.setDescription("Description of test task");
        taskDto.setStatus(Status.TODO);
        taskDto.setDateTime(LocalDateTime.now());

        Set<ConstraintViolation<TaskDto>> violations = validator.validate(taskDto);
        assertEquals(0, violations.size());
    }

    @Test
    void testInvalidTaskDtoWithoutTitle() {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(1L);
        taskDto.setDescription("Description of test task");
        taskDto.setStatus(Status.TODO);
        taskDto.setDateTime(LocalDateTime.now());

        Set<ConstraintViolation<TaskDto>> violations = validator.validate(taskDto, OnCreate.class);
        assertFalse(violations.isEmpty());
    }
}
