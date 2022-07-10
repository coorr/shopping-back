package shopping.coor.auth.application.exception;

import shopping.coor.kernel.application.exception.ApplicationLogicException;

public class UserAlreadyExistsException extends ApplicationLogicException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
