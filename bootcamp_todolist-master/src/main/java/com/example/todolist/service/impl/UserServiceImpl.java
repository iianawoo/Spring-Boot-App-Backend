package com.example.todolist.service.impl;

import com.example.todolist.domain.exception.ResourceNotFoundException;
import com.example.todolist.domain.user.Role;
import com.example.todolist.domain.user.User;
import com.example.todolist.repository.TaskRepository;
import com.example.todolist.repository.UserRepository;
import com.example.todolist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public User getByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("not found"));
    }

    @Override
    @Transactional
    public User update(User user) {
        User updatedUser = userRepository.findById(user.getId()).orElseThrow(() -> new ResourceNotFoundException("not found"));
        updatedUser.setName(user.getName());
        updatedUser.setUsername(user.getUsername());
        updatedUser.setRole(Role.ROLE_USER);
        updatedUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(updatedUser);
        return updatedUser;

    }

    @Override
    @Transactional
    public User create(User user) {
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new IllegalStateException("user with that name already exist");
        }
        if(!user.getPassword().equals(user.getPasswordConfirmation())){
            throw new IllegalStateException("password not same");
        }
        user.setRole(Role.ROLE_USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;

    }

    @Override
    @Transactional
    public void insertUserRole(Long userId, Role role) {

    }

    @Override
    @Transactional
    public boolean isTaskOwner(Long userId, Long taskId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("not found user"));
        User taskOwner = taskRepository.findById(taskId).get().getUser();
        return user.getId().equals(taskOwner.getId()) && user.getUsername().equals(taskOwner.getUsername());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }


}
