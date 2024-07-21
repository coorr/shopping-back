package shopping.coor.domain.item;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import shopping.coor.domain.item.dto.*;
import shopping.coor.domain.item.exception.ItemNotFoundException;
import shopping.coor.domain.item.image.Image;
import shopping.coor.domain.item.image.ImageRepository;
import shopping.coor.domain.item.image.dto.ImageUpdateReqDto;

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
public class ItemService {

    @Value("${cloud.aws.s3.bucket:@null}")
    public String bucket;
    private static final int FIRST_SIZE = 0;

    private final ItemRepository itemRepository;
    private final ItemQueryRepository itemQueryRepository;
    private final ImageRepository imageRepository;

    private final AmazonS3 amazonS3;
    private final Executor executor;

    public Item getItemById(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(ItemNotFoundException::new);
    }

    public ItemGetResDto getItem(Long itemId) {
        Item items = getItemById(itemId);

        return new ItemGetResDto(items);
    }

    public List<ItemsGetResDto> getItems(ItemSearchGetReqDto dto) {
        return itemQueryRepository.findAllBySearchConditions(dto)
                .stream()
                .map(ItemsGetResDto::new)
                .collect(Collectors.toList());
    }
    @Transactional
    public void delete(Long itemId) {
        Item items = this.getItemById(itemId);

        items.delete();
    }


    @Transactional
    public Long createItem(MultipartFile[] multipartFiles, ItemCreateReqDto itemCreateReqDto) {
        Item items = itemCreateReqDto.toEntity();
        List<Image> images = new ArrayList<>();

        if (multipartFiles == null)  {
            Item result = itemRepository.save(items);
            return result.getId();
        }

        for (MultipartFile file : multipartFiles) {
            String fileName = getFileName(file);
            images.add(Image.createImage(fileName, items));
        }
        items.addImage(images);
        Item result = itemRepository.save(items);
        return result.getId();
    }

    @Transactional
    public Boolean updateItem(Long itemId, MultipartFile[] multipartFiles, ItemUpdateReqDto itemUpdateReqDto) {
        Item items = itemQueryRepository.getItemList(itemId);
        List<Image> images = new ArrayList<>();
        List<Long> imageId = existImageByIdDelete(itemUpdateReqDto, items);

        items.update(itemUpdateReqDto);
        System.out.println("imageId = " + imageId);
        imageRepository.deleteAllByIdInBatch(imageId);

        if (multipartFiles != null) {
            for (MultipartFile file : multipartFiles) {
                String fileName = getFileName(file);
                images.add(Image.createImage(fileName, items));
            }

            items.addImage(images);
            items.update(itemUpdateReqDto);

            return Boolean.TRUE;
        }

        return Boolean.TRUE;
    }

    private List<Long> existImageByIdDelete(ItemUpdateReqDto itemUpdateReqDto, Item items) {
        List<Long> imageId = items.getImages().stream()
                .map(id -> id.getId())
                .collect(Collectors.toList());

        for (ImageUpdateReqDto imageUpdateReqDto : itemUpdateReqDto.getImagePath()) {
            imageId.remove(imageUpdateReqDto.getId());
        }

        return imageId;
    }

    private String getFileName(MultipartFile file) {
        String fileName = createFileName(file.getOriginalFilename());

        Runnable runnable = () -> {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try (InputStream inputStream = file.getInputStream()) {
//                    S3 임시 주석
                    amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead));
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        executor.execute(runnable);
        return fileName;
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
