package com.example.todolist.service;

import com.example.todolist.domain.user.Role;
import com.example.todolist.domain.user.User;

public interface UserService {
    User getById(Long id);
    User getByUsername(String username);

    User update(User user);
    User create(User user);
    void insertUserRole(Long userId, Role role);
    boolean isTaskOwner(Long userId, Long taskId);

    void deleteById(Long id);
}
