package shopping.coor.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shopping.coor.model.Order;
import shopping.coor.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o join fetch o.delivery where o.user = ?1 and o.orderDate between ?3 and ?2 order by o.id desc")
    List<Order> getOrderUserById(User user, LocalDateTime startDate, LocalDateTime endDate);
}
