package com.example.todolist.web.mappers;

import com.example.todolist.domain.user.User;
import com.example.todolist.web.dto.user.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);
    User toEntity(UserDto userDto);
}
