package com.example.todolist.web.controller;

import com.example.todolist.domain.user.User;
import com.example.todolist.service.AuthService;
import com.example.todolist.service.UserService;
import com.example.todolist.web.dto.auth.JwtRequest;
import com.example.todolist.web.dto.auth.JwtResponse;
import com.example.todolist.web.dto.user.UserDto;
import com.example.todolist.web.dto.validation.OnCreate;
import com.example.todolist.web.mappers.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@Validated
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;




    @PostMapping("/login")
    public JwtResponse login(@Validated @RequestBody JwtRequest jwtRequest) {
        return authService.login(jwtRequest);
    }



    @PostMapping("/register")
    public UserDto register(@Validated(OnCreate.class) @RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User createdUser = userService.create(user);
        return userMapper.toDto(createdUser);
    }


    @PostMapping("/refresh")
    public JwtResponse refresh(@RequestBody String token){
        return authService.refresh(token);
    }

}
