package com.example.todolist.web.mappers;

import com.example.todolist.domain.task.Status;
import com.example.todolist.domain.task.Task;
import com.example.todolist.web.dto.task.TaskDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskMapperTest {

    private final TaskMapper taskMapper = Mappers.getMapper(TaskMapper.class);

    @Test
    void testToDto() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setDescription("Description");
        task.setStatus(Status.TODO);
        task.setDateTime(LocalDateTime.now());

        TaskDto taskDto = taskMapper.toDto(task);

        assertEquals(task.getId(), taskDto.getId());
        assertEquals(task.getTitle(), taskDto.getTitle());
        assertEquals(task.getDescription(), taskDto.getDescription());
        assertEquals(task.getStatus(), taskDto.getStatus());
        assertEquals(task.getDateTime(), taskDto.getDateTime());
    }

    @Test
    void testToEntity() {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(1L);
        taskDto.setTitle("Test Task");
        taskDto.setDescription("Description");
        taskDto.setStatus(Status.TODO);
        taskDto.setDateTime(LocalDateTime.now());

        Task task = taskMapper.toEntity(taskDto);

        assertEquals(taskDto.getId(), task.getId());
        assertEquals(taskDto.getTitle(), task.getTitle());
        assertEquals(taskDto.getDescription(), task.getDescription());
        assertEquals(taskDto.getStatus(), task.getStatus());
        assertEquals(taskDto.getDateTime(), task.getDateTime());
    }
}

