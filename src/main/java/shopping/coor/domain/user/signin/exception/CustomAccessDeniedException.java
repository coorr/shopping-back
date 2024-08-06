package shopping.coor.domain.user.signin.exception;

public class CustomAccessDeniedException extends IllegalStateException {
    public CustomAccessDeniedException() {
        super("권한이 없습니다.");
    }
}
