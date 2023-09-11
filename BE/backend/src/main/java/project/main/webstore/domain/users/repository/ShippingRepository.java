package project.main.webstore.domain.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.main.webstore.domain.users.entity.ShippingInfo;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface ShippingRepository extends JpaRepository<ShippingInfo, Long> {
    Optional<ShippingInfo> findByInfoId(Long infoId);
}
