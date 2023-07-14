package ru.practicum.user.model;

import lombok.Builder;
import lombok.Data;
import ru.practicum.item.model.Item;
import ru.practicum.user.enums.UserState;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

@Data
@Builder
@Entity
@Table(name = "users", schema = "public")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "registration_date")
    private Instant registrationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private UserState userState;

    @OneToMany
    private Set<Item> items;
}
