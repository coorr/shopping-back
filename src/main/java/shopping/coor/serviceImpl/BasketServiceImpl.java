package shopping.coor.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.coor.model.Basket;
import shopping.coor.model.Item;
import shopping.coor.model.User;
import shopping.coor.payload.request.BasketRequestDto;
import shopping.coor.payload.response.BasketResponseDto;
import shopping.coor.repository.BasketRepository;
import shopping.coor.repository.ItemRepository;
import shopping.coor.repository.UserRepository;
import shopping.coor.service.BasketService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Transactional
    @Override
    public ResponseEntity<?> basketAddUser(Long userId, List<BasketRequestDto> basketRequestDto) throws Exception {
        User userById = userRepository.getById(userId);
        ArrayList<Long> arrayOnlyById = basketRepository.findArrayOnlyById(userById);
        System.out.println("arrayOnlyById = " + arrayOnlyById);

        Basket basket = new Basket();
        for (BasketRequestDto requestDto : basketRequestDto) {
            if (arrayOnlyById.contains(requestDto.getKeyIndex())) {
                Basket basketDuplicate = basketRepository.getById(requestDto.getKeyIndex());
                basketDuplicate.setItemTotal(basketDuplicate.getItemTotal() + requestDto.getItemTotal());
                basketDuplicate.setItemCount(basketDuplicate.getItemCount() + requestDto.getItemCount());
                continue;
            }
            basket.setId(requestDto.getKeyIndex());
            Item itemId = itemRepository.getItemEntity(requestDto.getItemId());
            User userIds = userRepository.getById(userId);
            basket.setItem(itemId);
            basket.setUser(userIds);
            basket.setItemCount(requestDto.getItemCount());
            basket.setSize(requestDto.getSize());
            basket.setItemTotal(requestDto.getItemTotal());
            basketRepository.save(basket);
        }
        return null;
    }


    @Override
    public ResponseEntity<?> getBasketByUserId(Long userid) {
        User userById = userRepository.getById(userid);
        List<Basket> basketList = basketRepository.findAllByUserId(userById);

        return ResponseEntity.ok(basketResponseDtos(basketList));
    }

    @Transactional
    @Override
    public ResponseEntity<?> removeBasketById(Long basketId, Long userId) {
        basketRepository.deleteById(basketId);
        User userById = userRepository.getById(userId);
        List<Basket> basketList = basketRepository.findAllByUserId(userById);

        return ResponseEntity.ok(basketResponseDtos(basketList));
    }

    @Transactional
    @Override
    public ResponseEntity<?> downBasketUserById(Long basketId, Long userId) {
        Basket itemPrice = basketRepository.findItemPriceById(basketId);
        basketRepository.updateCountDownById(basketId, itemPrice.getItem().getDiscountPrice());
        User userById = userRepository.getById(userId);
        List<Basket> basketList = basketRepository.findAllByUserId(userById);

        return ResponseEntity.ok(basketResponseDtos(basketList));
    }

    @Transactional
    @Override
    public ResponseEntity<?> plusBasketUserById(Long basketId, Long userId) {
        Basket itemPrice = basketRepository.findItemPriceById(basketId);
        basketRepository.updateCountPlusById(basketId, itemPrice.getItem().getDiscountPrice());
        User userById = userRepository.getById(userId);
        List<Basket> basketList = basketRepository.findAllByUserId(userById);

        return ResponseEntity.ok(basketResponseDtos(basketList));
    }

    @Transactional
    @Override
    public void basketEmpty(Long userId) {
        User userById = userRepository.getById(userId);
        basketRepository.deleteBasketByUserId(userById);
    }

    @Transactional
    @Override
    public ResponseEntity<?> insertNotUserBasket(Long userId, List<BasketRequestDto> basketDto) {
        User userById = userRepository.getById(userId);
        Basket basket = new Basket();
        for (BasketRequestDto requestDto : basketDto) {
            Item itemById = itemRepository.getById(requestDto.getItemId());
            Basket duplicateBasket = basketRepository.findItemByIdUserByIdSize(itemById, requestDto.getSize(), userById);
            System.out.println("duplicateBasket = " + duplicateBasket);
            if(duplicateBasket != null) {
                duplicateBasket.setItemTotal(duplicateBasket.getItemTotal() + requestDto.getItemTotal());
                duplicateBasket.setItemCount(duplicateBasket.getItemCount() + requestDto.getItemCount());
                continue;
            }
            basket.setId(Long.parseLong(requestDto.getKeyIndex()+""+userId));
            basket.setItem(itemById);
            basket.setUser(userById);
            basket.setItemCount(requestDto.getItemCount());
            basket.setSize(requestDto.getSize());
            basket.setItemTotal(requestDto.getItemTotal());
            basketRepository.save(basket);
        }
        List<Basket> basketList = basketRepository.findAllByUserId(userById);
        return ResponseEntity.ok(basketResponseDtos(basketList));
    }

    protected List<BasketResponseDto> basketResponseDtos(List<Basket> basketList) {
        return basketList.stream().map(b -> new BasketResponseDto(b)).collect(Collectors.toList());
    }
}
