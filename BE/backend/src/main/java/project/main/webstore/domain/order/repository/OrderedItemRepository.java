package project.main.webstore.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.main.webstore.domain.order.dto.OrderDBMonthlyPriceDto;
import project.main.webstore.domain.order.entity.OrderedItem;

import java.util.List;

public interface OrderedItemRepository extends JpaRepository<OrderedItem,Long> {
    @Query("SELECT new project.main.webstore.domain.order.dto.OrderDBItemSaleDto(i.itemId,i.itemName,SUM(oi.price),SUM(oi.discountedPrice)) " +
            "FROM OrderedItem oi " +
            "JOIN oi.item i " +
            "GROUP BY oi.item.itemId ")
    List<OrderDBMonthlyPriceDto> itemSales();

}
