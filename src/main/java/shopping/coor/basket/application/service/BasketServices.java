package shopping.coor.basket.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.coor.auth.application.service.UserService;
import shopping.coor.auth.domain.User.User;
import shopping.coor.basket.application.exception.BasketNotFoundException;
import shopping.coor.basket.domain.Basket;
import shopping.coor.basket.domain.BasketRepository;
import shopping.coor.basket.presentation.http.request.BasketPostReqDto;
import shopping.coor.basket.presentation.http.request.BasketPutReqDto;
import shopping.coor.basket.presentation.http.response.BasketGetResDto;
import shopping.coor.item.application.service.ItemService;
import shopping.coor.item.domain.Item;
import shopping.coor.kernel.presentation.response.SimpleBooleanResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasketServices {

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
                item.stockCheck(dto);

                basketDuplicate.updateBasket(dto.getItemTotal(), dto.getItemCount());
                continue;
            }

            item.stockCheck(dto);
            basketList.add(dto.toBasket(dto, user, item));
        }
        basketRepository.saveAll(basketList);

        return userId;
    }

    public void checkBasket(List<BasketPostReqDto> basketPostReqDto) {
        Item item = itemService.getItemById(basketPostReqDto.get(0).getItemId());
        item.stockCheck(basketPostReqDto.get(0));
    }

    @Transactional
    public List<BasketGetResDto> putBaskets(Long userId, List<BasketPutReqDto> basketPutReqDto) {
        List<Basket> basketList = new ArrayList<>();
        User user = userService.getUserById(userId);

        for (BasketPutReqDto dto : basketPutReqDto) {
            Basket basket = basketRepository.findBasketAndItemByIdAndSizeAndUserById(dto.getItemId(), dto.getSize(), userId);
            // 기존 장바구니에 있으면 수량 변경
            if (basket != null) {
                basket.updateBasket(dto.getItemTotal(), dto.getItemCount());
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
    public SimpleBooleanResponse removeBasket(Long basketId, Long userId) {
        Basket basket = getBasketById(basketId);
        basket.updateSize();
        basketRepository.deleteById(basketId);
        return new SimpleBooleanResponse(true);
    }

    private List<BasketGetResDto> basketDtoResponseChange(List<Basket> basketList) {
        return basketList.stream().map(b -> new BasketGetResDto(b)).collect(Collectors.toList());
    }

    private Basket getBasketById(Long basketId) {
        return basketRepository.findById(basketId).orElseThrow(() -> new BasketNotFoundException());
    }


}
