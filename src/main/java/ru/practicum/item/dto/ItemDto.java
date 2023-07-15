package ru.practicum.item.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class ItemDto {

    private Long id;
    private String url;
    private Set<String> tags;
}
