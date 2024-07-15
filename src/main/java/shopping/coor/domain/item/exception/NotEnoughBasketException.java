package shopping.coor.domain.item.exception;

import shopping.coor.common.exception.ApplicationLogicException;

public class NotEnoughBasketException extends ApplicationLogicException {

    public NotEnoughBasketException() {
        super("최소 주문수량은 1개입니다.");
    }


}
