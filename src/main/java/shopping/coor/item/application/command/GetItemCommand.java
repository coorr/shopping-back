package shopping.coor.item.application.command;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import shopping.coor.item.application.exception.ItemNotFoundException;
import shopping.coor.item.domain.Item;
import shopping.coor.item.domain.ItemRepository;
import shopping.coor.item.presentation.http.response.ItemGetResDto;
import shopping.coor.item.presentation.http.response.ItemsGetResDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetItemCommand {
    private static final int FIRST_SIZE = 0;
    private final ItemRepository repository;

    public ItemGetResDto getItemCommand(@NonNull final Long itemId) {
        Item item = repository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("상품을 찾을 수 없습니다."));

        return new ItemGetResDto(item);
    }


    public List<ItemsGetResDto> getItemsCommand(@NonNull Long itemLastId,@NonNull int size, String category) {
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

    public void deleteItem(@NonNull Long itemId) {
        Item item = repository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("상품을 찾을 수 없습니다."));
        repository.delete(item);
    }

    private List<ItemsGetResDto> getItemChangeDto(List<Item> items) {
        return items.stream()
                .map(i -> new ItemsGetResDto(i))
                .collect(Collectors.toList());
    }

}
