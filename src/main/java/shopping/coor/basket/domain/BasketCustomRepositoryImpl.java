package shopping.coor.basket.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shopping.coor.auth.domain.User.User;

import java.util.List;

import static shopping.coor.auth.domain.User.QUser.user;
import static shopping.coor.item.domain.QItem.item;
import static shopping.coor.basket.domain.QBasket.basket;

@Repository
@RequiredArgsConstructor
public class BasketCustomRepositoryImpl implements BasketCustomRepository {
    private final JPAQueryFactory query;

    @Override
    public List<Basket> findBasketAndItemAndUserById(User userId) {
        return query
                .selectFrom(basket)
                .innerJoin(user).on(basket.user.id.eq(user.id))
                .innerJoin(item).on(basket.item.id.eq(item.id))
                .where(basket.user.eq(userId))
                .fetch();
    }

    public Basket findBasketAndItemByIdAndSizeAndUserById(Long itemId, String size, Long userId) {
        return query
                .selectFrom(basket)
                .where(basket.item.id.eq(itemId)
                        .and(basket.size.eq(size))
                        .and(basket.user.id.eq(userId)))
                .fetchOne();
    }

    @Override
    public List<Long> findByUser(User user) {
        return query.select(basket.id)
                .from(basket)
                .where(basket.user.id.eq(user.getId()))
                .fetch();

    }

}
