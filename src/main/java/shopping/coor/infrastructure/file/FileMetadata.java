package shopping.coor.infrastructure.file;


import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Getter
public class FileMetadata {
    private final String originalFilename;
    private final String storedFilename;
    private final long size;
    private final String contentType;

    public FileMetadata(MultipartFile file) {
        this.originalFilename = file.getOriginalFilename();
        this.storedFilename = createStoredFilename(file.getOriginalFilename());
        this.size = file.getSize();
        this.contentType = file.getContentType();
    }

    private String createStoredFilename(String originalFilename) {
        return UUID.randomUUID().toString() + getFileExtension(originalFilename);
    }

    private String getFileExtension(String filename) {
        try {
            return filename.substring(filename.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new InvalidFileExtensionException();
        }
    }
}
