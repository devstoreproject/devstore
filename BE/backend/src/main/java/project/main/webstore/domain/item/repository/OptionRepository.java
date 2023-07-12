package project.main.webstore.domain.item.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.main.webstore.domain.item.entity.ItemOption;
import project.main.webstore.domain.item.entity.ItemSpec;

import java.util.List;
import java.util.Optional;

public interface OptionRepository extends JpaRepository<ItemOption, Long> {
    @Query("SELECT i FROM ItemOption i WHERE i.item.detail = :detail")
    Page<ItemOption> findByDetail(@Param("detail")String detail, Pageable pageRequest);
    @Query("SELECT i FROM ItemOption i WHERE i.item.id = :itemId")
    List<ItemOption> findAllByItemId(@Param("itemId") Long itemId);
}
