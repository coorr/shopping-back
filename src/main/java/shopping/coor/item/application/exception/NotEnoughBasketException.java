package shopping.coor.item.application.exception;

import shopping.coor.common.application.exception.ApplicationLogicException;

public class NotEnoughBasketException extends ApplicationLogicException {

    public NotEnoughBasketException() {
        super("최소 주문수량은 1개입니다.");
    }


}
