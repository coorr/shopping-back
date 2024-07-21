package shopping.coor.domain.user.signin.exception;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException() {
        super("유효하지 않는 토큰입니다.");
    }
}
