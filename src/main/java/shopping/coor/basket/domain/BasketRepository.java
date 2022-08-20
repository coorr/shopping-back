package shopping.coor.basket.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shopping.coor.auth.domain.User.User;

import java.util.List;


@Repository
public interface BasketRepository extends JpaRepository<Basket, Long>, BasketCustomRepository {

    void deleteAllByUser(User user);

    void deleteById(Long id);

    List<Basket> findAllByUser(User user);

    @Query("select b from Basket b join fetch b.user join fetch b.item where b.user = ?1")
    List<Basket> findAllByUserId(User userId);




}

