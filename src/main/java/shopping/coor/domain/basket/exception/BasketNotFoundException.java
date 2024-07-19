package shopping.coor.domain.basket.exception;

import shopping.coor.common.exception.NotFoundException;

public class BasketNotFoundException extends NotFoundException {
    public BasketNotFoundException() {
        super("장바구니 상품을 찾을 수 없습니다.");
    }
}
