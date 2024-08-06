package shopping.coor.domain.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shopping.coor.domain.item.dto.ItemCreateReqDto;
import shopping.coor.domain.item.dto.ItemUpdateReqDto;
import shopping.coor.domain.item.exception.ItemNotFoundException;
import shopping.coor.domain.item.image.Image;
import shopping.coor.domain.item.image.ImageService;
import shopping.coor.domain.item.image.dto.ImageUpdateReqDto;
import shopping.coor.infrastructure.file.FileMetadata;
import shopping.coor.infrastructure.file.FileStorageService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemAuthService {
    private final ItemRepository itemRepository;
    private final FileStorageService s3FileStorageService;
    private final ImageService imageService;

    private Item getItem(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(ItemNotFoundException::new);
    }

    @Transactional
    public void delete(Long itemId) {
        Item item = this.getItem(itemId);
        item.delete();
    }

    @Transactional
    public Item create(MultipartFile[] files, ItemCreateReqDto dto) {
        Item item = dto.toEntity();
        item = itemRepository.save(item);

        uploadAndSaveImages(files, item);
        return item;
    }

    @Transactional
    public void update(Long itemId, MultipartFile[] files, ItemUpdateReqDto dto) {
        Item item = this.getItem(itemId);
        item.update(dto);

        List<Long> imageIds = getImageIdsToDelete(dto, item);
        imageService.deleteAll(imageIds);

        uploadAndSaveImages(files, item);
    }

    private void uploadAndSaveImages(MultipartFile[] files, Item item) {
        if (files != null) {
            List<FileMetadata> fileMetadataList = s3FileStorageService.upload(files);

            List<Image> images = new ArrayList<>();
            for (FileMetadata metadata : fileMetadataList) {
                Image image = new Image(metadata.getStoredFilename(), item);
                images.add(image);
            }
            imageService.saveAll(images);
            item.setItem(images);
        }
    }

    private List<Long> getImageIdsToDelete(ItemUpdateReqDto dto, Item item) {
        List<Long> imageIds = dto.getImagePath().stream()
                .map(ImageUpdateReqDto::getId)
                .collect(Collectors.toList());

        return item.getImages().stream()
                .map(Image::getId).filter(id -> !imageIds.contains(id))
                .collect(Collectors.toList());
    }
}
