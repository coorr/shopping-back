package shopping.coor.infrastructure.file;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileStorageService {
    List<FileMetadata> upload(MultipartFile[] files);
}
