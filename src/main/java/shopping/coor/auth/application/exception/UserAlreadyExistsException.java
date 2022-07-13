package shopping.coor.auth.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import shopping.coor.kernel.application.exception.ApplicationLogicException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAlreadyExistsException extends ApplicationLogicException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
