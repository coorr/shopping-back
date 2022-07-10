package shopping.coor.auth.application.exception;

import shopping.coor.kernel.application.exception.ApplicationLogicException;

public class UserNotFoundException extends ApplicationLogicException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
