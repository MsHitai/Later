package ru.practicum.user.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.config.PersistenceConfig;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.enums.UserState;
import ru.practicum.user.mapper.UserMapper;
import ru.practicum.user.model.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestPropertySource(properties = {"db.name=test"})
@SpringJUnitConfig({PersistenceConfig.class, UserServiceImpl.class})
class UserServiceImplTest {

    private final EntityManager em;
    private final UserService service;

    @Test
    void saveUser() {
        UserDto userDto = makeUserDto("some@email.com", "Пётр", "Иванов");
        service.saveUser(userDto);

        TypedQuery<User> query = em.createQuery("Select u from User u where u.email = :email", User.class);
        User user = query
                .setParameter("email", userDto.getEmail())
                .getSingleResult();


        assertThat(user.getId(), notNullValue());
        assertThat(user.getFirstName(), equalTo(userDto.getFirstName()));
        assertThat(user.getLastName(), equalTo(user.getLastName()));
        assertThat(user.getEmail(), equalTo(userDto.getEmail()));
        assertThat(user.getUserState(), equalTo(userDto.getState()));
        assertThat(user.getRegistrationDate(), notNullValue());
    }

    @Test
    void testFindAll() {
        UserDto userDto1 = makeUserDto("test@test.ru", "Peter", "Ivanov");
        UserDto userDto2 = makeUserDto("test@test.ru", "Ivan", "Petrov");

        service.saveUser(userDto1);
        service.saveUser(userDto2);

        List<User> users = service.getAllUsers().stream().map(UserMapper::mapToUser).collect(Collectors.toList());

        User firstCheck = users.get(0);
        User secondCheck = users.get(1);

        assertThat(users, hasSize(2));
        assertThat(firstCheck.getFirstName(), equalTo(userDto1.getFirstName()));
        assertThat(firstCheck.getLastName(), equalTo(userDto1.getLastName()));
        assertThat(firstCheck.getEmail(), equalTo(userDto1.getEmail()));

        assertThat(secondCheck.getFirstName(), equalTo(userDto2.getFirstName()));
        assertThat(secondCheck.getLastName(), equalTo(userDto2.getLastName()));
        assertThat(secondCheck.getEmail(), equalTo(userDto2.getEmail()));
    }

    private UserDto makeUserDto(String email, String firstName, String lastName) {
        UserDto dto = UserDto.builder().build();
        dto.setEmail(email);
        dto.setFirstName(firstName);
        dto.setLastName(lastName);
        dto.setState(UserState.ACTIVE);
        dto.setRegistrationDate(Instant.now());

        return dto;
    }
}