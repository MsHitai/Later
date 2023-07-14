package ru.practicum.user.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.model.User;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class UserMapper {

    public User mapToUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .userState(userDto.getUserState())
                .firstName(mapToFirstName(userDto.getFullName()))
                .lastName(mapToSecondName(userDto.getFullName()))
                .registrationDate(mapDateToInstant(userDto.getRegistrationDate()))
                .build();
    }

    public UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .fullName(mapToFullName(user.getFirstName(), user.getLastName()))
                .email(user.getEmail())
                .registrationDate(mapToDate(user.getRegistrationDate()))
                .userState(user.getUserState())
                .build();
    }

    public String mapToFullName(String userFirstName, String userLastName) {
        return userFirstName + userLastName;
    }

    public String mapToDate(Instant registrationTime) {
        return DateTimeFormatter
                .ofPattern("yyyy.MM.dd hh:mm:ss")
                .withZone(ZoneOffset.UTC)
                .format(registrationTime);
    }

    public Instant mapDateToInstant(String date) {
        return Instant.parse(date);
    }

    public String mapToFirstName(String fullName) {
        return fullName.split(" ")[0];
    }

    public String mapToSecondName(String fullName) {
        return fullName.split(" ")[1];
    }
}
