package shopping.coor.domain.item;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static shopping.coor.domain.item.QItem.item;
import static shopping.coor.domain.item.image.QImage.image;

@Repository
@RequiredArgsConstructor
public class ItemCustomRepositoryImpl implements ItemCustomRepository {

    private final JPAQueryFactory query;

    @Override
    public Item getItemList(Long itemId) {
        return query.selectFrom(item)
                .leftJoin(image).on(image.items.id.eq(item.id))
                .where(item.id.eq(itemId))
                .fetchOne();
    }

}
