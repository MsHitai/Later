package ru.practicum.user.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.user.enums.UserState;

@Data
@Builder
public class UserDto {
    private Long id;
    private String fullName;
    private String email;
    private String registrationDate;
    private UserState userState;
}
