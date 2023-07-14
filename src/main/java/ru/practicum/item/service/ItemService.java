package ru.practicum.item.service;

import ru.practicum.item.dto.ItemDto;
import ru.practicum.item.model.Item;

import java.util.List;

public interface ItemService {
    List<ItemDto> getItems(long userId);

    ItemDto addNewItem(Long userId, Item item);

    void deleteItem(long userId, long itemId);

}
