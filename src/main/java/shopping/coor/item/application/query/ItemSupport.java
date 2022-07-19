package shopping.coor.item.application.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ItemSupport {

    private final JPAQueryFactory queryFactory;

    public ItemSupport(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

//    public void insertItemImage

}

