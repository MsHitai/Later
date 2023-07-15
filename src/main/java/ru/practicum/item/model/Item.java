package ru.practicum.item.model;

import lombok.Builder;
import lombok.Data;
import ru.practicum.note.model.ItemNote;
import ru.practicum.user.model.User;

import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@Entity
@Table(name = "items", schema = "public")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "url", nullable = false)
    private String url;

    @ElementCollection
    @CollectionTable(name = "tags", joinColumns = @JoinColumn(name = "item_id"))
    @Column(name = "name")
    private Set<String> tags;

    @OneToMany
    private Set<ItemNote> notes;
}
