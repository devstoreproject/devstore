package project.main.webstore.domain.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.main.webstore.domain.item.entity.ItemSpec;

public interface SpecRepository extends JpaRepository<ItemSpec, Long> {

}
