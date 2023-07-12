package ru.practicum.item.model;

import lombok.Data;

@Data
public class Item {
    private Long id;
    private Long userId;
    private String url;
}
