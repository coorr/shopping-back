package shopping.coor.domain.basket;

import shopping.coor.domain.user.User;

import java.util.List;

public interface BasketCustomRepository {
    List<Basket> findBasketAndItemAndUserById(User userId);

    Basket findBasketAndItemByIdAndSizeAndUserById(Long itemId, String size, Long userId);

    List<Long> findByUser(User user);

}
