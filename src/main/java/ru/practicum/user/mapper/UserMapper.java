package ru.practicum.user.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.model.User;

@UtilityClass
public class UserMapper {

    public User mapToUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .userState(userDto.getState())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .registrationDate(userDto.getRegistrationDate())
                .build();
    }

    public UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .registrationDate(user.getRegistrationDate())
                .state(user.getUserState())
                .build();
    }


}
