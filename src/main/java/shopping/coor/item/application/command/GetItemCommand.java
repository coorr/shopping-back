package shopping.coor.item.application.command;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import shopping.coor.item.application.exception.ItemNotFoundException;
import shopping.coor.item.domain.Item;
import shopping.coor.item.domain.ItemRepository;
import shopping.coor.item.presentation.http.request.ItemCreateReqDto;
import shopping.coor.item.presentation.http.response.ItemGetResDto;
import shopping.coor.item.presentation.http.response.ItemsGetResDto;
import shopping.coor.model.Image;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class GetItemCommand {

    @Value("${cloud.aws.s3.bucket:@null}")
    public String bucket;
    private static final int FIRST_SIZE = 0;

    private final ItemRepository repository;
    private final AmazonS3 amazonS3;
    private final Executor executor;

    public ItemGetResDto getItemCommand( final Long itemId) {
        Item item = repository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("상품을 찾을 수 없습니다."));

        return new ItemGetResDto(item);
    }


    public List<ItemsGetResDto> getItemsCommand( Long itemLastId, int size, String category) {
        PageRequest pageRequest = PageRequest.of(0, size);

        if (itemLastId == FIRST_SIZE && category.equals("null")) {
            List<Item> items = repository.findByIdGreaterThanOrderByIdDesc(itemLastId, pageRequest);
            return getItemChangeDto(items);
        }
        if (category.equals("null")) {
            List<Item> items = repository.findByIdLessThanOrderByIdDesc(itemLastId, pageRequest);
            return getItemChangeDto(items);
        }
        if (itemLastId == 0) {
            List<Item> items = repository.findByIdGreaterThanAndCategoryOrderByIdDesc(itemLastId, category, pageRequest);
            return getItemChangeDto(items);
        }
        List<Item> items = repository.findByIdLessThanAndCategoryOrderByIdDesc(itemLastId, category, pageRequest);
        return getItemChangeDto(items);
    }

    @Transactional
    public void deleteItem( Long itemId) {
        Item item = repository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("상품을 찾을 수 없습니다."));
        repository.delete(item);
    }


    @Transactional
    public void postItemSave(MultipartFile[] multipartFiles, ItemCreateReqDto itemCreateReqDto) {
        List<Image> images = new ArrayList<>();
        Item item = itemCreateReqDto.toEntity();

        if (multipartFiles == null)  {
            Item result = repository.save(item);
        }

        for (MultipartFile file : multipartFiles) {
            String fileName = createFileName(file.getOriginalFilename());

            Runnable runnable = () -> {
                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentLength(file.getSize());
                objectMetadata.setContentType(file.getContentType());

                try (InputStream inputStream = file.getInputStream()) {
                    amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };
            executor.execute(runnable);
            images.add(Image.createImage(fileName, item));
        }
        item.setImages(images);
        repository.save(item);
        }




    private List<ItemsGetResDto> getItemChangeDto(List<Item> items) {
        return items.stream()
                .map(i -> new ItemsGetResDto(i))
                .collect(Collectors.toList());
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

}
