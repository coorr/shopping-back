package shopping.coor.basket.domain;

import shopping.coor.auth.domain.User.User;

import java.util.List;

public interface BasketCustomRepository {
    List<Basket> findBasketAndItemAndUserById(User userId);

    Basket findBasketAndItemByIdAndSizeAndUserById(Long itemId, String size, Long userId);

    List<Long> findByUser(User user);

}
