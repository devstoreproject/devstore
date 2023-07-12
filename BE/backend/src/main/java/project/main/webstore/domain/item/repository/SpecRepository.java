package project.main.webstore.domain.item.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.main.webstore.domain.item.entity.ItemSpec;

public interface SpecRepository extends JpaRepository<ItemSpec, Long> {
    @Query("SELECT i FROM ItemSpec i WHERE i.item.specs = :specs")
    Page<ItemSpec> findBySpecs(String specs, Pageable pageRequest);
}
