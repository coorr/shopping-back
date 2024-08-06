package shopping.coor.infrastructure.file;

import shopping.coor.common.exception.SimpleMessageIllegalStateException;

public class InvalidFileExtensionException extends SimpleMessageIllegalStateException {
    public InvalidFileExtensionException() {
        super("접근할 수 없는 파일 확장자입니다.");
    }
}
