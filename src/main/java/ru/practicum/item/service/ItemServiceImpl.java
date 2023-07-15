package ru.practicum.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.item.dto.ItemDto;
import ru.practicum.item.mapper.ItemMapper;
import ru.practicum.item.model.Item;
import ru.practicum.item.repository.ItemRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<ItemDto> getItems(long userId) {
        List<Item> userItems = repository.findByUserId(userId);
        return ItemMapper.mapToItemDto(userItems);
    }

    @Transactional
    @Override
    public ItemDto addNewItem(Long userId, Item item) {

        return ItemMapper.mapToItemDto(repository.save(item));
    }

    @Transactional
    @Override
    public void deleteItem(long userId, long itemId) {
        repository.deleteByUserIdAndId(userId, itemId);
    }
}
