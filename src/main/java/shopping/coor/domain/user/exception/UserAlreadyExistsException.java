package shopping.coor.domain.user.exception;

import shopping.coor.common.exception.SimpleMessageIllegalArgumentException;

public class UserAlreadyExistsException extends SimpleMessageIllegalArgumentException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }

}
