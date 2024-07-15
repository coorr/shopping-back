package shopping.coor.domain.basket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.coor.domain.user.UserService;
import shopping.coor.domain.user.User;
import shopping.coor.domain.basket.enums.BasketOrder;
import shopping.coor.domain.basket.exception.BasketNotFoundException;
import shopping.coor.domain.basket.dto.BasketItemPostGetDto;
import shopping.coor.domain.basket.dto.BasketPostReqDto;
import shopping.coor.domain.basket.dto.BasketPutReqDto;
import shopping.coor.domain.basket.dto.BasketGetResDto;
import shopping.coor.common.container.SimpleBooleanResponse;
import shopping.coor.domain.item.ItemService;
import shopping.coor.domain.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasketService {

    private final BasketRepository basketRepository;
    private final UserService userService;
    private final ItemService itemService;

    public List<BasketGetResDto> getBaskets(Long userId) {
        User user = userService.getUserById(userId);
        List<Basket> basketList = basketRepository.findBasketAndItemAndUserById(user);
        return basketDtoResponseChange(basketList);
    }

    @Transactional
    public Long postBaskets(Long userId, List<BasketPostReqDto> basketPostReqDto) {
        List<Basket> basketList = new ArrayList<>();

        User user = userService.getUserById(userId);
        List<Long> basketIds = basketRepository.findByUser(user);

        for (BasketPostReqDto dto : basketPostReqDto) {
            Item item = itemService.getItemById(dto.getItemId());

            if (basketIds.contains(dto.getKeyIndex())) {
                Basket basketDuplicate = getBasketById(dto.getKeyIndex());
                basketDuplicate.updateBasket(dto.getItemTotal(), dto.getItemCount());
                continue;
            }

            basketList.add(dto.toBasket(dto, user, item));
        }
        basketRepository.saveAll(basketList);

        return userId;
    }

    public void checkBasket(List<BasketPostReqDto> basketPostReqDto) {
        for (BasketPostReqDto dto : basketPostReqDto) {
            Item item = itemService.getItemById(dto.getItemId());
            item.stockCheck(dto.getItemCount(), dto.getSize());
        }
    }

    public SimpleBooleanResponse checkItem(Long itemId, BasketItemPostGetDto dto) {
        Item item = itemService.getItemById(itemId);
        item.stockCheck(dto.getItemCount()+1 , dto.getSize());
        return new SimpleBooleanResponse(true);
    }

    @Transactional
    public List<BasketGetResDto> putBaskets(Long userId, List<BasketPutReqDto> basketPutReqDto) {
        List<Basket> basketList = new ArrayList<>();
        User user = userService.getUserById(userId);

        for (BasketPutReqDto dto : basketPutReqDto) {
            Basket isBasket = basketRepository.findBasketAndItemByIdAndSizeAndUserById(dto.getItemId(), dto.getSize(), userId);
            // 기존 장바구니에 있으면 수량 변경
            if (isBasket != null) {
                isBasket.update(dto.getItemTotal(), dto.getItemCount());
                continue;
            }

            Item item = itemService.getItemById(dto.getItemId());
            basketList.add(dto.toBasket(dto, user, item));
        }
        basketRepository.saveAll(basketList);
        List<Basket> result = basketRepository.findBasketAndItemAndUserById(user);

        return basketDtoResponseChange(result);
    }

    @Transactional
    public SimpleBooleanResponse deleteBasket(Long basketId) {
        basketRepository.deleteById(basketId);
        return new SimpleBooleanResponse(true);
    }
    @Transactional
    public List<BasketGetResDto> updateCount(Long basketId, Long userId, BasketOrder order) {
        Basket basket = getBasketById(basketId);
        basket.updateCount(basket.getItem().getDiscountPrice(), order);

        basketRepository.save(basket);
        User user = userService.getUserById(userId);
        List<Basket> result = basketRepository.findAllByUser(user);
        return basketDtoResponseChange(result);
    }
    @Transactional
    public SimpleBooleanResponse deleteAllBasket(Long userId) {
        User user = userService.getUserById(userId);
        basketRepository.deleteAllByUser(user);
        return new SimpleBooleanResponse(true);
    }

    private List<BasketGetResDto> basketDtoResponseChange(List<Basket> basketList) {
        return basketList.stream().map(b -> new BasketGetResDto(b)).collect(Collectors.toList());
    }

    public Basket getBasketById(Long basketId) {
        return basketRepository.findById(basketId).orElseThrow(() -> new BasketNotFoundException());
    }

    public List<Basket> findBasketByUserId(User user) {
        return basketRepository.findAllByUser(user);
    }
}
