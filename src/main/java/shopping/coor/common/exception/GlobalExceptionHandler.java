package shopping.coor.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import shopping.coor.common.container.ErrorsResponse;

import java.io.IOException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ClientAbortException.class)
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public void handleClientAbortException(ClientAbortException e) {
        log.info("ClientAbortException is occurred. {}", e.toString());
    }

    @ExceptionHandler(ValidationIllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsResponse handleValidationIllegalArgumentException(ValidationIllegalArgumentException e) {
        log.info(e.getErrors().toString(), e);
        return ErrorsResponse.create(e.getErrors());
    }

    @ExceptionHandler(SimpleMessageIllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsResponse handleSimpleMessageIllegalArgumentException(SimpleMessageIllegalArgumentException e) {
        log.info(e.toString(), e);
        return ErrorsResponse.create(e.getMessage(), e.getField());
    }

    @ExceptionHandler(SimpleMessageIllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsResponse handleSimpleMessageIllegalStateException(SimpleMessageIllegalStateException e) {
        log.info(e.toString(), e);
        return ErrorsResponse.create(e.getMessage(), e.getField());
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorsResponse handleIOException(IOException e) {
        log.error(e.toString(), e);
        return ErrorsResponse.create("치명적인 에러가 발생하였습니다. 관리자에게 문의하세요", null);
    }

//    @ExceptionHandler(ExpiredLoginSessionException.class)
//    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
//    public ErrorsResponse handleValidationExpiredLoginSessionException(ExpiredLoginSessionException e, HttpServletRequest request) {
//        log.info(e.toString(), e);
//
//        return ErrorsResponse.create(e.getMessage(), null);
//    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorsResponse handleNotFoundException(NotFoundException e) {
        log.info(e.toString(), e);
        return ErrorsResponse.create(e.getMessage(), null);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorsResponse handleUnhandledException(Exception e) {
        log.error(e.toString(), e);
        return ErrorsResponse.create("치명적인 에러가 발생하였습니다. 관리자에게 문의하세요.", null);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorsResponse handleNoHandlerFoundException(Exception e) {
        log.error(e.toString(), e);
        return ErrorsResponse.create("URL not found", null);
    }



//    @ExceptionHandler(ApplicationLogicException.class)
//    public ResponseEntity<ErrorsResponse> handleValidationUserAlreadyExistsException(ApplicationLogicException e) {
//        if (e.getMessage() != null)
//            log.error(e.getMessage(), e);
//
//        ErrorsResponse response = ErrorsResponse.create().message(e.getMessage());
//
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ErrorsResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
//        if (e.getMessage() != null)
//            log.error(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
//
//        ErrorsResponse response = ErrorsResponse.create()
//                .message(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(BindException.class)
//    public ResponseEntity<ErrorsResponse> handleBindException(BindException e) {
//        if (e.getMessage() != null)
//            log.error(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
//
//        ErrorsResponse response = ErrorsResponse.create()
//                .message(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
//
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(ValidationIllegalArgumentException.class)
//    public ResponseEntity<ErrorsResponse> handleValidationIllegalArgumentException(ValidationIllegalArgumentException e) {
//        if (e.getMessage() != null)
//            log.error(e.getMessage());
//
//        ErrorsResponse response = ErrorsResponse.create()
//                .message(e.getMessage());
//
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }


}