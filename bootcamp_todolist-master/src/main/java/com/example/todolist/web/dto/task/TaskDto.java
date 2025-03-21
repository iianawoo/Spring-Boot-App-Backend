package com.example.todolist.web.dto.task;

import com.example.todolist.domain.task.Status;
import com.example.todolist.web.dto.validation.OnCreate;
import com.example.todolist.web.dto.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
@Data
public class TaskDto {
    @NotNull(message = "id must be not null", groups = OnUpdate.class)
    private Long id;
    @NotNull(message = "title must be not null", groups = {OnUpdate.class, OnCreate.class})
    @Length(max = 255, message = "title length must be less than 255 symbols", groups = {OnUpdate.class, OnCreate.class})
    private String title;
    @Length(max = 255, message = "description length must be less than 255 symbols", groups = {OnUpdate.class, OnCreate.class})
    private String description;

    private Status status;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:MM")
    private LocalDateTime dateTime;

    @NotNull(message = "userId must be not null", groups = OnCreate.class)
    private Long userId;
}
