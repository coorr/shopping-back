package shopping.coor.auth.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import shopping.coor.common.application.exception.ApplicationLogicException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserNotFoundException extends ApplicationLogicException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
