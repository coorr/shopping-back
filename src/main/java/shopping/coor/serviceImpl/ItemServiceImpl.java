package shopping.coor.serviceImpl;

import com.amazonaws.annotation.ThreadSafe;
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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import shopping.coor.model.Image;
import shopping.coor.model.Item;
import shopping.coor.repository.item.dto.ItemRequestDto;
import shopping.coor.repository.item.dto.ItemRequestOneDto;
import shopping.coor.repository.user.dto.MessageResponse;
import shopping.coor.repository.image.ImageRepository;
import shopping.coor.repository.item.ItemRepository;
import shopping.coor.service.ItemService;


import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@ThreadSafe
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
    private final Executor executor;

    @Override
    public List<ItemRequestDto> getItemAll(Long lastId, int size, String category) {
        System.out.println("category = " + category);
        PageRequest pageRequest = PageRequest.of(0, size);

        if (lastId == 0 && category.equals("null")) {
            List<Item> item = itemRepository.getItemFirst(lastId, pageRequest);
            List<ItemRequestDto> result = getItemChangeDto(item);
            return result;
        }
        if (category.equals("null")) {
            List<Item> item = itemRepository.getItemAll(lastId, pageRequest);
            List<ItemRequestDto> result = getItemChangeDto(item);
            return result;
        }
        if (lastId == 0) {
            List<Item> itemCategory = itemRepository.getItemFirstCategory(lastId, category, pageRequest);
            List<ItemRequestDto> result = getItemChangeDto(itemCategory);
            return result;
        }
        List<Item> itemCategory = itemRepository.getItemCategory(lastId, category, pageRequest);
        List<ItemRequestDto> result = getItemChangeDto(itemCategory);


        return result;
    }

    @Override
    public ResponseEntity<?> getItemOne(Long id) {
        List<Item> item = itemRepository.getItemOne(id);
        if (item.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("존재하지 않는 게시글입니다."));
        }
        List<ItemRequestOneDto> result = item.stream()
                .map(i -> new ItemRequestOneDto(i))
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);

    }

    @Transactional
    @Override
    public void removeItem(Long id) {
        Item item = itemRepository.getItemEntity(id);
        itemRepository.delete(item);
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
            imageRepository.saveImage(fileName, itemId);
        }

        return null;
    }

    @Transactional
    @Override
    public ResponseEntity<?> revisedItem(MultipartFile[] multipartFiles, String itemData, String imagePath) throws Exception {
        Item itemDto = new ObjectMapper().readValue(itemData, Item.class);
        Long itemId = itemDto.getId();
        Item itemList = itemRepository.updateItemOne(itemId);
        itemList.setTitle(itemDto.getTitle());
        itemList.setPrice(itemDto.getPrice());
        itemList.setDiscountPrice(itemDto.getDiscountPrice());
        itemList.setQuantityS(itemDto.getQuantityS());
        itemList.setQuantityM(itemDto.getQuantityM());
        itemList.setQuantityL(itemDto.getQuantityL());
        itemList.setCategory(itemDto.getCategory());
        itemList.setSize(itemDto.getSize());
        itemList.setMaterial(itemDto.getMaterial());
        itemList.setInfo(itemDto.getInfo());

        List<Image> images = imageRepository.getItemToImage(itemId);
        Image[] imageDto = new ObjectMapper().readValue(imagePath, Image[].class);
        Set<Long> imageIds = images.stream().map(id -> id.getId()).collect(Collectors.toSet());

        for (Image imageList : imageDto) {
            Image image = new Image();
            image.setId(imageList.getId());
            image.setLocation(imageList.getLocation());
            image.setItem(itemDto);
            imageRepository.saveAndFlush(image);

            imageIds.remove(imageList.getId());
        }
        if (imageIds.size() != 0) {
            imageRepository.deleteImage(imageIds);
        }


        if (multipartFiles == null) {
            return null;
        }
        for (int i = 0; i < multipartFiles.length; i++) {
            MultipartFile file = multipartFiles[i];

            String fileName = createFileName(file.getOriginalFilename());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

//            try (InputStream inputStream = file.getInputStream()) {
//                amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
//                        .withCannedAcl(CannedAccessControlList.PublicRead));
//            } catch (IOException e) {
//                return ResponseEntity
//                        .badRequest()
//                        .body(new MessageResponse("파일 업로드에 실패했습니다."));
//            }

            imageRepository.saveImage(fileName, itemDto);
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
