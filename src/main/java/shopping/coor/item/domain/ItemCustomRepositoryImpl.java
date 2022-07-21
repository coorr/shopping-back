package shopping.coor.item.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static shopping.coor.item.domain.QItem.item;
import static shopping.coor.model.QImage.image;

@Repository
@RequiredArgsConstructor
public class ItemCustomRepositoryImpl implements ItemCustomRepository {

    private final JPAQueryFactory query;

    @Override
    public Item getItemList(Long itemId) {
        return query.selectFrom(item)
                .leftJoin(image).on(image.item.id.eq(item.id))
                .where(item.id.eq(itemId))
                .fetchOne();
    }

}
