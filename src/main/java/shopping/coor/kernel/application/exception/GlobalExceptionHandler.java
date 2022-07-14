package shopping.coor.kernel.application.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import shopping.coor.auth.application.exception.UserAlreadyExistsException;
import shopping.coor.kernel.application.error.response.ErrorsResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private MessageSource messageSource;

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ErrorsResponse handleValidationUserAlreadyExistsException(UserAlreadyExistsException e, Locale locale, HttpServletRequest request) {
        if (e.getMessage() != null)
            log.error(e.getMessage(), e);
        return ErrorsResponse.create(messageSource.getMessage(e.getMessageCode(), null, locale));
    }

    @ExceptionHandler(ValidationIllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsResponse handleValidationIllegalArgumentException(ValidationIllegalArgumentException e, Locale locale, HttpServletRequest request) {
        if (e.getMessage() != null)
            log.error(e.getMessage(), e);
        return ErrorsResponse.create(e.getErrors(), messageSource, locale, request.getAttribute("X-REDIRECTION-URL").toString());
    }


}
