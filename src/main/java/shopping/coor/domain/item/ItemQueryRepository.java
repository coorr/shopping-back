package shopping.coor.domain.item;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import shopping.coor.domain.item.dto.ItemSearchGetReqDto;
import shopping.coor.domain.item.enums.ItemCategory;

import java.util.List;

import static shopping.coor.domain.item.QItem.item;
import static shopping.coor.domain.item.image.QImage.image;

@Repository
@RequiredArgsConstructor
public class ItemQueryRepository {

    private final JPAQueryFactory query;

    public Item getItemList(Long itemId) {
        return query.selectFrom(item)
                .leftJoin(image).on(image.item.id.eq(item.id))
                .where(item.id.eq(itemId))
                .fetchOne();
    }

    public List<Item> findAllBySearchConditions(ItemSearchGetReqDto dto) {
        Pageable pageable = dto.generatePage();
        return query.selectFrom(item)
                .where(
                        this.eqItemCategory(dto.getCategory()),
                        this.ltItemLastId(dto.getItemLastId())
                )
                .orderBy(item.id.desc())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanExpression eqItemCategory(String category) {
        if (StringUtils.hasLength(category)) {
            return item.category.eq(ItemCategory.valueOf(category));
        }
        return null;
    }

    private BooleanExpression ltItemLastId(String itemLastId) {
        if (StringUtils.hasLength(itemLastId)) {
            return item.id.lt(Long.valueOf(itemLastId));
        }
        return null;
    }

}
