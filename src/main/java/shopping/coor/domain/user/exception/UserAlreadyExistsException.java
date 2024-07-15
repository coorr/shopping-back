package shopping.coor.domain.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import shopping.coor.common.exception.ApplicationLogicException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAlreadyExistsException extends ApplicationLogicException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }

}
