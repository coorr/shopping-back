package shopping.coor.common.application.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import shopping.coor.common.application.error.response.ErrorsResponse;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(ApplicationLogicException.class)
    public ResponseEntity<ErrorsResponse> handleValidationUserAlreadyExistsException(ApplicationLogicException e) {
        if (e.getMessage() != null)
            log.error(e.getMessage(), e);

        ErrorsResponse response = ErrorsResponse.create().message(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorsResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        if (e.getMessage() != null)
            log.error(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());

        ErrorsResponse response = ErrorsResponse.create()
                .message(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorsResponse> handleBindException(BindException e) {
        if (e.getMessage() != null)
            log.error(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());

        ErrorsResponse response = ErrorsResponse.create()
                .message(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationIllegalArgumentException.class)
    public ResponseEntity<ErrorsResponse> handleValidationIllegalArgumentException(ValidationIllegalArgumentException e) {
        if (e.getMessage() != null)
            log.error(e.getMessage());

        ErrorsResponse response = ErrorsResponse.create()
                .message(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
