package com.example.todolist.service;

import com.example.todolist.web.dto.auth.JwtRequest;
import com.example.todolist.web.dto.auth.JwtResponse;

public interface AuthService {

    JwtResponse login(JwtRequest jwtRequest);
    JwtResponse refresh(String refreshToken);
}
