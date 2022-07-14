package shopping.coor.auth.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import shopping.coor.kernel.application.exception.ApplicationLogicException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAlreadyExistsException extends ApplicationLogicException {

    private String messageCode;
    public UserAlreadyExistsException(String message) {
        super(message);
        this.messageCode = message;
    }

    public String getMessageCode() {
        return messageCode;
    }
}
