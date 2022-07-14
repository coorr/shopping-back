package shopping.coor.repository.basket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shopping.coor.model.Basket;
import shopping.coor.item.domain.Item;
import shopping.coor.auth.domain.User.User;

import java.util.ArrayList;
import java.util.List;


@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {

    void deleteById(Long id);

    @Query("select b from Basket b join fetch b.user join fetch b.item where b.user = ?1")
    List<Basket> findAllByUserId(User userId);

    @Query("select b from Basket b join fetch b.item join fetch b.user where b.id = ?1")
    Basket findItemPriceById(Long basketId);

    @Modifying(clearAutomatically=true)
    @Query("update Basket b set b.itemCount = b.itemCount - 1, b.itemTotal = b.itemTotal - ?2 where b.id = ?1")
    void updateCountDownById(Long basketId, int price);

    @Modifying(clearAutomatically=true)
    @Query("update Basket b set b.itemCount = b.itemCount + 1, b.itemTotal = b.itemTotal + ?2 where b.id = ?1")
    void updateCountPlusById(Long basketId, int price);

    @Query("select b.id from Basket b where b.user = ?1")
    ArrayList<Long> findArrayOnlyById(User userId);

    @Modifying(clearAutomatically = true)
    @Query("delete from Basket b where b.user = ?1")
    void deleteBasketByUserId(User userId);

    @Query("select b from Basket b join fetch b.item join fetch b.user where b.item = ?1 and b.size = ?2 and b.user = ?3")
    Basket findItemByIdUserByIdSize(Item itemId, String size, User userId);




}

