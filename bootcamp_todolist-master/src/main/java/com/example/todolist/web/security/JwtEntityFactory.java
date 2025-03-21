package com.example.todolist.web.security;

import com.example.todolist.domain.user.User;

public class JwtEntityFactory{
    public static JwtEntity toJwtEntity(User user){
        return new JwtEntity(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getPassword(),
                user.getRole()
        );
    }
}
