package ru.practicum.item.service;

import ru.practicum.item.model.Item;

import java.util.List;

public interface ItemService {
    List<Item> getItems(long userId);

    Item addNewItem(Long userId, Item item);

    void deleteItem(long userId, long itemId);

}
