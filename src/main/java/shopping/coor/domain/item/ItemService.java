package shopping.coor.domain.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.coor.domain.item.dto.ItemSearchGetReqDto;
import shopping.coor.domain.item.dto.ItemsGetResDto;
import shopping.coor.domain.item.exception.ItemNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemQueryRepository itemQueryRepository;

    public Item getItem(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(ItemNotFoundException::new);
    }

    public List<ItemsGetResDto> getItems(ItemSearchGetReqDto dto) {
        return itemQueryRepository.findAllBySearchConditions(dto)
                .stream()
                .map(ItemsGetResDto::new)
                .collect(Collectors.toList());
    }
}
