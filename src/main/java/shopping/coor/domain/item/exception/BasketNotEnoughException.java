package shopping.coor.domain.item.exception;

import shopping.coor.common.exception.SimpleMessageIllegalArgumentException;

public class BasketNotEnoughException extends SimpleMessageIllegalArgumentException {

    public BasketNotEnoughException() {
        super("최소 주문수량은 1개입니다.");
    }


}
