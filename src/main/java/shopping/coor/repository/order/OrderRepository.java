package shopping.coor.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shopping.coor.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
