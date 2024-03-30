package com.popov.tasklist.web.mappers;

import com.popov.tasklist.domain.user.User;
import com.popov.tasklist.web.dto.user.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
     UserDTO toDto(User user);
     User toEntity(UserDTO userDto);
}
