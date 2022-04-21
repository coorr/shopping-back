package shopping.coor.serviceImpl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import shopping.coor.model.Image;
import shopping.coor.model.Item;
import shopping.coor.payload.request.ItemRequestDto;
import shopping.coor.payload.response.MessageResponse;
import shopping.coor.repository.ImageRepository;
import shopping.coor.repository.ItemRepository;
import shopping.coor.service.ItemService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    private final ItemRepository itemRepository;
    private final ImageRepository imageRepository;
    private final AmazonS3 amazonS3;


    @Override
    public List<ItemRequestDto> getItem(Long lastId, int size) {
        PageRequest pageRequest = PageRequest.of(0, size);

        if (lastId == 0) {
            List<Item> item = itemRepository.getItemFirst(lastId, pageRequest);
            List<ItemRequestDto> result = getItemChangeDto(item);
            return result;
        }

        List<Item> item = itemRepository.getItem(lastId, pageRequest);
        List<ItemRequestDto> result = getItemChangeDto(item);
        return result;
    }

    private List<ItemRequestDto> getItemChangeDto(List<Item> item) {
        return item.stream()
                .map(i -> new ItemRequestDto(i))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ResponseEntity<?> insertItemAll(MultipartFile[] multipartFiles, String itemData) throws Exception {
        Item itemDto = new ObjectMapper().readValue(itemData, Item.class);
        Item itemId = itemRepository.save(itemDto);

        if (multipartFiles == null) {
            return null;
        }

        for (int i = 0; i < multipartFiles.length; i++) {
            MultipartFile file = multipartFiles[i];

            String fileName = createFileName(file.getOriginalFilename());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try (InputStream inputStream = file.getInputStream()) {
                amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
            } catch (IOException e) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("파일 업로드에 실패했습니다."));
//                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
            }

            imageRepository.saveImage(fileName, itemId);
        }
        return null;
    }

    public void deleteFile(String fileName) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }

    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName) { // file 형식이 잘못된 경우를 확인하기 위해 만들어진 로직이며, 파일 타입과 상관없이 업로드할 수 있게 하기 위해 .의 존재 유무만 판단하였습니다.
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
        }
    }

    @Override
    public List<Image> getImage() {
        return imageRepository.findAll();
    }
}
