package shopping.coor.item.application.exception;

import shopping.coor.kernel.application.exception.ApplicationLogicException;

public class ItemNotFoundException extends ApplicationLogicException {
    public ItemNotFoundException() {
        super("상품을 찾을 수 없습니다.");
    }
}
