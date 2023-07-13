package project.main.webstore.domain.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.main.webstore.domain.item.entity.ItemOption;

import java.util.List;

public interface OptionRepository extends JpaRepository<ItemOption, Long> {
    @Query("SELECT i FROM ItemOption i WHERE i.item.itemId = :itemId")
    List<ItemOption> findAllByItemId(@Param("itemId") Long itemId);
}
