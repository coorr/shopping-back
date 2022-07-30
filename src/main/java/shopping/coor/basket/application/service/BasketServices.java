package shopping.coor.basket.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.coor.auth.application.service.UserService;
import shopping.coor.auth.domain.User.User;
import shopping.coor.basket.domain.Basket;
import shopping.coor.basket.domain.BasketRepository;
import shopping.coor.basket.presentation.http.request.BasketPostReqDto;
import shopping.coor.basket.presentation.http.response.BasketGetResDto;
import shopping.coor.item.application.service.ItemService;
import shopping.coor.item.domain.Item;

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
        User user = userService.getUserById(userId);
        Basket basket = basketRepository.findByUser(user);

        for (BasketPostReqDto basketDto : basketPostReqDto) {
            Item item = itemService.getItemById(basketDto.getItemId());
            item.postCheck(basketDto);
        }
        return null;
    }

    public void checkBasket(List<BasketPostReqDto> basketPostReqDto) {
        Item item = itemService.getItemById(basketPostReqDto.get(0).getItemId());
        item.postCheck(basketPostReqDto.get(0));
    }

    @Transactional
    public List<BasketGetResDto> putBaskets(Long userId, List<BasketPostReqDto> basketPostReqDto) {
        List<Basket> basketList = new ArrayList<>();
        User user = userService.getUserById(userId);

        for (BasketPostReqDto dto : basketPostReqDto) {
            Basket basket = basketRepository.findBasketAndItemByIdAndSizeAndUserById(dto.getItemId(), dto.getSize(), userId);
            if (basket != null) {
                basket.updateBasket(dto);
                continue;
            }

            Item item = itemService.getItemById(dto.getItemId());
            basketList.add(dto.toBasket(dto, user, item));
        }
        basketRepository.saveAll(basketList);
        List<Basket> result = basketRepository.findBasketAndItemAndUserById(user);

        return basketDtoResponseChange(result);
    }

    private List<BasketGetResDto> basketDtoResponseChange(List<Basket> basketList) {
        return basketList.stream().map(b -> new BasketGetResDto(b)).collect(Collectors.toList());
    }
}
