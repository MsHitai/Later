package ru.practicum.item.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.item.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepositoryImpl implements ItemRepository{

    Map<Long, Item> items = new HashMap<>();
    long itemId = 0;

    @Override
    public List<Item> findByUserId(long userId) {
        List<Item> itemsToReturn = new ArrayList<>();
        for (Map.Entry<Long, Item> itemEntry : items.entrySet()) {
            if (itemEntry.getValue().getUserId() == userId) {
                itemsToReturn.add(itemEntry.getValue());
            }
        }
        return itemsToReturn;
    }

    @Override
    public Item save(Item item) {
        item.setId(++itemId);
        return items.put(item.getId(), item);
    }

    @Override
    public void deleteByUserIdAndItemId(long userId, long itemId) {
        List<Item> itemsToRemove = new ArrayList<>();
        for (Map.Entry<Long, Item> itemEntry : items.entrySet()) {
            if (itemEntry.getValue().getUserId() == userId
                    && itemEntry.getValue().getId() == itemId) {
                itemsToRemove.add(itemEntry.getValue());
            }
        }

        for (Item item : itemsToRemove) {
            items.remove(item.getId());
        }
    }
}
