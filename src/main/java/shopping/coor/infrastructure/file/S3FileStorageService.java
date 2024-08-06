package shopping.coor.infrastructure.file;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3FileStorageService implements FileStorageService {
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList(".png", ".jpg", ".jpeg");
    @Value("${cloud.aws.s3.bucket:@null}")
    private String bucket;
    private final AmazonS3 amazonS3;
    private final Executor executor;


    @Override
    public List<FileMetadata> upload(MultipartFile[] files) {
        if (files == null) {
            return Collections.emptyList();
        }
        validateFileExtensions(files);

        List<FileMetadata> fileMetadataList = new ArrayList<>();
        for (MultipartFile file : files) {
            FileMetadata metadata = new FileMetadata(file);
            fileMetadataList.add(metadata);

            Runnable runnable = () -> {
                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentLength(file.getSize());
                objectMetadata.setContentType(file.getContentType());

                try (InputStream inputStream = file.getInputStream()) {
                    /** AWS S3 오픈 시 활성화 */
//                    PutObjectRequest request = new PutObjectRequest(bucket, metadata.getStoredFilename(), inputStream, objectMetadata)
//                            .withCannedAcl(CannedAccessControlList.PublicRead);
//                    amazonS3.putObject(request);
                } catch (Exception e) {
                    log.error("file upload failed message : {} " , e.getMessage());
                    throw new FileUploadException();
                }
            };
            executor.execute(runnable);
        }

        return fileMetadataList;
    }

    private void validateFileExtensions(MultipartFile[] files) {
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            if (fileName == null || !hasAllowedExtension(fileName, ALLOWED_EXTENSIONS)) {
                throw new InvalidFileExtensionException();
            }
        }
    }

    private boolean hasAllowedExtension(String fileName, List<String> allowedExtensions) {
        return allowedExtensions.stream()
                .anyMatch(extension -> fileName.toLowerCase().endsWith(extension));
    }
}
