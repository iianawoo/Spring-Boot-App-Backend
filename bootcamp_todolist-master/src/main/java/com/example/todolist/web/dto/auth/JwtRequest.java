package com.example.todolist.web.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequest {
    @NotNull(message = "username must be not null")
    private String username;
    @NotNull(message = "password must be not null")
    private String password;

}
