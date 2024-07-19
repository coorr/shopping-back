package shopping.coor.domain.item.exception;

import shopping.coor.common.exception.NotFoundException;

public class ItemNotFoundException extends NotFoundException {
    public ItemNotFoundException() {
        super("상품을 찾을 수 없습니다.");
    }
}
