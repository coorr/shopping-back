package shopping.coor.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shopping.coor.domain.delivery.DeliveryStatus;
import shopping.coor.domain.user.User;
import shopping.coor.domain.order.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o join fetch o.delivery where o.user = ?1 and o.createdAt between ?3 and ?2 order by o.id desc")
    List<Order> getOrderUserById(User user, LocalDateTime startDate, LocalDateTime endDate);

    @Query("select o from Order o join fetch o.delivery where o.user = ?1 and o.createdAt between ?3 and ?2 and o.status = ?4 order by o.id desc")
    List<Order> getOrderStatusUserById(User user, LocalDateTime startDate, LocalDateTime endDate, OrderStatus status);

    @Query("select o from Order o join fetch o.delivery d where o.user = ?1 and o.createdAt between ?3 and ?2 and d.status = ?4 order by o.id desc")
    List<Order> getOrderDeliverStatusUserById(User user, LocalDateTime startDate, LocalDateTime endDate, DeliveryStatus status);
}
