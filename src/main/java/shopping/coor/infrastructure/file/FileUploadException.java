package shopping.coor.infrastructure.file;

import shopping.coor.common.exception.SimpleMessageIllegalStateException;

public class FileUploadException extends SimpleMessageIllegalStateException {
    public FileUploadException() {
        super("파일 업로드 실패했습니다.");
    }
}
