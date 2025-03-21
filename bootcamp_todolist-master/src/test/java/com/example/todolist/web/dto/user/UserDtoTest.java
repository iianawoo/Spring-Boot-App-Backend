package com.example.todolist.web.dto.user;

import com.example.todolist.web.dto.validation.OnCreate;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class UserDtoTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidUserDto() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("Test User");
        userDto.setUsername("testuser");
        userDto.setPassword("password123");
        userDto.setPasswordConfirmation("password123");

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertEquals(0, violations.size());
    }

    @Test
    void testInvalidUserDtoWithoutName() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setUsername("testuser");
        userDto.setPassword("password123");
        userDto.setPasswordConfirmation("password123");

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto, OnCreate.class);
        assertFalse(violations.isEmpty());
    }
}
