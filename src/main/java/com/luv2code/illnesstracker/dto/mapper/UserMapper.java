package com.luv2code.illnesstracker.dto.mapper;

import com.luv2code.illnesstracker.domain.User;
import com.luv2code.illnesstracker.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "id", source = "user.id"),
            @Mapping(target = "firstName", source = "user.firstName"),
            @Mapping(target = "lastName", source = "user.lastName"),
            @Mapping(target = "email", source = "user.email"),
            @Mapping(target = "username", source = "user.username"),
            @Mapping(target = "phoneNumber", source = "user.phoneNumber"),
            @Mapping(target = "dateOfBirth", source = "user.dateOfBirth"),
            @Mapping(target = "gender", source = "user.gender")
    })
    UserDto toUserDto(final User user);

    @Mappings({
            @Mapping(target = "id", source = "user.id"),
            @Mapping(target = "firstName", source = "user.firstName"),
            @Mapping(target = "lastName", source = "user.lastName"),
            @Mapping(target = "email", source = "user.email"),
            @Mapping(target = "username", source = "user.username"),
            @Mapping(target = "phoneNumber", source = "user.phoneNumber"),
            @Mapping(target = "dateOfBirth", source = "user.dateOfBirth"),
            @Mapping(target = "gender", source = "user.gender")
    })
    List<UserDto> toUsersDto(final List<User> users);

}
