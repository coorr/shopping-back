package shopping.coor.item.application.exception;

import shopping.coor.common.application.exception.ApplicationLogicException;

public class NotEnoughStockException extends ApplicationLogicException {

    public NotEnoughStockException() {
        super("품절된 상품으로 주문할 수 없습니다.");
    }

    public NotEnoughStockException(String title) {
        super("상품의 수량이 재고수량 보다 많습니다. \n\n제품명 : " + title);
    }

}
