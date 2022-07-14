package shopping.coor.item.application.exception;

import shopping.coor.kernel.application.exception.ApplicationLogicException;

public class ItemNotFoundException extends ApplicationLogicException {
    public ItemNotFoundException(String message) {
        super(message);
    }
}
