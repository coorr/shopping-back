package shopping.coor.domain.item.image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    @Transactional
    public void saveAll(List<Image> images) {
        if (ObjectUtils.isEmpty(images)) {
            return;
        }
        imageRepository.saveAll(images);
    }

    @Transactional
    public void deleteAll(List<Long> imageIds) {
        if (ObjectUtils.isEmpty(imageIds)) {
            return;
        }
        imageRepository.deleteAllByIdInBatch(imageIds);
    }
}
