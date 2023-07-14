package ru.practicum.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import ru.practicum.item.model.Item;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item> {

    List<Item> findByUserId(long userId);

    /*@Query("DELETE FROM items i USING user_item ui WHERE i.id = ui.item_id and user_id = ?1 and item_id = ?2")
    void deleteByUserIdAndItemId(long userId, long itemId);*/
}
