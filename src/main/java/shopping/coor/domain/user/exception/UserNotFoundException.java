package shopping.coor.domain.user.exception;

import shopping.coor.common.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException() {
        super("해당 사용자는 찾을 수 없습니다.");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
