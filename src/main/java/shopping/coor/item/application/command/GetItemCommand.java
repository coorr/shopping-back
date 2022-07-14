package shopping.coor.item.application.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopping.coor.item.application.exception.ItemNotFoundException;
import shopping.coor.item.domain.ItemRepository;
import shopping.coor.item.presentation.http.response.ItemGetResDto;
import shopping.coor.item.presentation.http.response.ItemImageGetResDto;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetItemCommand {

    private final ItemRepository repository;

    public ItemGetResDto getItemCommand(final Long itemId) {
        var item = repository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("상품을 찾을 수 없습니다."));


        return ItemGetResDto.builder()
                .itemId(item.getId())
                .title(item.getTitle())
                .price(item.getPrice())
                .discount_price(item.getDiscountPrice())
                .quantityS(item.getQuantityS())
                .quantityM(item.getQuantityM())
                .quantityL(item.getQuantityL())
                .category(item.getCategory())
                .size(item.getSize())
                .material(item.getMaterial())
                .info(item.getInfo())
                .images(item.getImages().stream()
                        .map(image -> new ItemImageGetResDto(image))
                        .collect(Collectors.toList()))
                .build();


    }

}
