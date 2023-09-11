package project.main.webstore.domain.item.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.item.enums.Category;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByItemId(Long itemId);
    Optional<Item> findByItemName(String itemName);
    @Query("SELECT i FROM Item i WHERE i.category = :category")
    Page<Item> findItemByCategory(@Param("category") Category category, Pageable pageable);
    @Query("SELECT i FROM Item i WHERE i.itemName LIKE %:itemName%")     // 대소문자 구분 없이 사용
    Page<Item> findByItemNameContainingIgnoreCase(@Param("itemName") String itemName, Pageable pageable);
}
