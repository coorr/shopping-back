package shopping.coor.domain.item.exception;

import shopping.coor.common.exception.ApplicationLogicException;

public class ItemNotFoundException extends ApplicationLogicException {
    public ItemNotFoundException() {
        super("상품을 찾을 수 없습니다.");
    }
}
