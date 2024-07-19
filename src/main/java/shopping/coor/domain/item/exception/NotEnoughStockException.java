package shopping.coor.domain.item.exception;

import shopping.coor.common.exception.SimpleMessageIllegalArgumentException;

public class NotEnoughStockException extends SimpleMessageIllegalArgumentException {

    public NotEnoughStockException() {
        super("품절된 상품으로 주문할 수 없습니다.");
    }

    public NotEnoughStockException(String title) {
        super("상품의 수량이 재고수량 보다 많습니다. \n\n제품명 : " + title);
    }

}
