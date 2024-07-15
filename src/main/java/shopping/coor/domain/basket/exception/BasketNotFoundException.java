package shopping.coor.domain.basket.exception;

import shopping.coor.common.exception.ApplicationLogicException;

public class BasketNotFoundException extends ApplicationLogicException {
    public BasketNotFoundException() {
        super("장바구니 상품을 찾을 수 없습니다.");
    }
}
