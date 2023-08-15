package project.main.webstore.domain.order.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.main.webstore.domain.order.dto.OrderDBItemSaleDto;
import project.main.webstore.domain.order.dto.OrderDBMonthlyPriceDto;
import project.main.webstore.domain.order.entity.Orders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    Optional<Orders> findByOrderNumber(String orderNumber);
    @EntityGraph(attributePaths = {"user","coupons","orderedItemList"})
    Optional<Orders> findAllByOrderNumber(String orderNumber);
    @EntityGraph(attributePaths = {"user"})
    Optional<Orders> findUserByOrderNumber(String orderNumber);
    @EntityGraph
    Optional<Orders> findAllByOrderId(Long orderId);
    @EntityGraph(attributePaths = {"user"})
    Optional<Orders> findByOrderId(Long orderId);

    @Query("SELECT o FROM Orders o Where o.user.id = :userId")
    Page<Orders> findAllByUserId(Pageable pageable,@Param("userId") Long userId);

    @Query("SELECT o FROM Orders o WHERE o.createdAt>= :month")
    Page<Orders> findByMonth(Pageable pageable,@Param("month") LocalDateTime month);
    @Query("SELECT o FROM Orders o Where o.user.id = :userId and o.createdAt>= :month ")
    Page<Orders> findByUserIdAndMonth(Pageable pageable,@Param("userId") Long userId,@Param("month") LocalDateTime month);

    @Query("SELECT new project.main.webstore.domain.order.dto.OrderDBMonthlyPriceDto(YEAR(o.createdAt), MONTH(o.createdAt), " +
            "SUM(oi.itemCount * oi.discountedPrice),SUM(oi.itemCount * oi.price)) " +
            "FROM Orders o " +
            "JOIN o.orderedItemList oi " +
            "GROUP BY YEAR(o.createdAt), MONTH(o.createdAt)")
    List<OrderDBMonthlyPriceDto> monthlyPrice();
    @Query("SELECT new project.main.webstore.domain.order.dto.OrderDBItemSaleDto(i.itemId, i.itemName, " +
            "SUM(oi.itemCount * oi.discountedPrice),SUM(oi.itemCount * oi.price)) " +
            "FROM Orders o " +
            "JOIN o.orderedItemList oi " +
            "JOIN oi.item i " +
            "GROUP BY YEAR(o.createdAt), MONTH(o.createdAt)")
    List<OrderDBItemSaleDto> itemSales();

}

