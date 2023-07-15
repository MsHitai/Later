package ru.practicum.note.model;

import lombok.Data;
import lombok.ToString;
import ru.practicum.item.model.Item;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@Table(name = "item_notes")
public class ItemNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @Column(name = "date_of_note")
    private Instant dateOfNote;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Item item;
}
