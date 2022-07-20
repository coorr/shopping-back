package shopping.coor.item.application.command;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
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
import shopping.coor.item.presentation.http.request.ItemUpdateReqDto;
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

    public ItemGetResDto getItem( final Long itemId) {
        Item item = getItemById(itemId);

        return new ItemGetResDto(item);
    }


    public List<ItemsGetResDto> getItems( Long itemLastId, int size, String category) {
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
        Item item = getItemById(itemId);
        repository.delete(item);
    }


    @Transactional
    public Long createItem(MultipartFile[] multipartFiles, ItemCreateReqDto itemCreateReqDto) {
        Item item = itemCreateReqDto.toEntity();
        List<Image> images = new ArrayList<>();

        if (multipartFiles == null)  {
            Item result = repository.save(item);
            return result.getId();
        }

        for (MultipartFile file : multipartFiles) {
            String fileName = getFileName(file);
            images.add(Image.createImage(fileName, item));
        }
        item.setImages(images);
        Item result = repository.save(item);
        return result.getId();
    }

    @Transactional
    public Boolean updateItem(Long itemId, MultipartFile[] multipartFiles, ItemUpdateReqDto itemUpdateReqDto) {
        Item item = getItemById(itemId);
        List<Image> images = getImages(itemUpdateReqDto, item);

        if (multipartFiles == null) {
            item.setImages(images);
            item.update(itemUpdateReqDto);
            repository.saveAndFlush(item);
            return Boolean.TRUE;
        }

        for (MultipartFile file : multipartFiles) {
            String fileName = getFileName(file);
            images.add(Image.createImage(fileName, item));
        }
        item.setImages(images);
        item.update(itemUpdateReqDto);
        repository.saveAndFlush(item);
        return Boolean.TRUE;
    }

    private String getFileName(MultipartFile file) {
        String fileName = createFileName(file.getOriginalFilename());

        Runnable runnable = () -> {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try (InputStream inputStream = file.getInputStream()) {
//                    amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
//                            .withCannedAcl(CannedAccessControlList.PublicRead));
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        executor.execute(runnable);
        return fileName;
    }

    private List<Image> getImages(ItemUpdateReqDto itemUpdateReqDto, Item item) {
        List<Image> images = new ArrayList<>();

        itemUpdateReqDto.getImagePath().stream()
                .map(image -> images.add(Image.createImage(image.getLocation(), item)))
                .collect(Collectors.toList());
        return images;
    }

    private Item getItemById(Long itemId) {
        return repository.findById(itemId).orElseThrow(
                () -> new ItemNotFoundException("상품을 찾을 수 없습니다."));
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

    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
        }
    }



}
