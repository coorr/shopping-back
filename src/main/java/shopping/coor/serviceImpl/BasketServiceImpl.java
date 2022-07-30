package shopping.coor.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import shopping.coor.basket.domain.Basket;
import shopping.coor.item.domain.Item;
import shopping.coor.auth.domain.User.User;
import shopping.coor.basket.presentation.http.request.BasketPostReqDto;
import shopping.coor.basket.presentation.http.response.BasketGetResDto;
import shopping.coor.auth.presentation.http.request.MessageResponse;
import shopping.coor.basket.domain.BasketRepository;
import shopping.coor.item.domain.ItemRepository;
import shopping.coor.auth.domain.User.UserRepository;
import shopping.coor.service.BasketService;

import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
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
    public ResponseEntity<MessageResponse> basketAddUser(Long userId, List<BasketPostReqDto> basketPostReqDto) throws Exception {
        System.out.println("테스트");
        User userById = userRepository.getById(userId);
        ArrayList<Long> arrayOnlyBasketId = basketRepository.findArrayOnlyById(userById);

        for (BasketPostReqDto requestDto : basketPostReqDto) {
            Item itemById = itemRepository.getById(requestDto.getItemId());

            String errorMessage = String.format("상품의 수량이 재고수량 보다 많습니다. \n\n제품명 : %s", itemById.getTitle());
            if (requestDto.getSize().equals("S")) {
                int quantitySizeSCount = itemRepository.findQuantitySizeSCount(requestDto.getItemId());
                if (arrayOnlyBasketId.contains(requestDto.getKeyIndex())) {
                    Basket basketDuplicate = basketRepository.getById(requestDto.getKeyIndex());
                    if (quantitySizeSCount < requestDto.getItemCount() + basketDuplicate.getItemCount()) {
                        return ResponseEntity.badRequest().body(new MessageResponse(errorMessage));
                    }
                } else if (quantitySizeSCount < requestDto.getItemCount()) {
                    return ResponseEntity.badRequest().body(new MessageResponse(errorMessage));
                }
            } else if (requestDto.getSize().equals("M")) {
                int quantitySizeMCount = itemRepository.findQuantitySizeMCount(requestDto.getItemId());
                if (arrayOnlyBasketId.contains(requestDto.getKeyIndex())) {
                    Basket basketDuplicate = basketRepository.getById(requestDto.getKeyIndex());
                    if (quantitySizeMCount < requestDto.getItemCount() + basketDuplicate.getItemCount()) {
                        return ResponseEntity.badRequest().body(new MessageResponse(errorMessage));
                    }
                } else if (quantitySizeMCount < requestDto.getItemCount()) {
                    return ResponseEntity.badRequest().body(new MessageResponse(errorMessage));
                }
            } else if (requestDto.getSize().equals("L")) {
                int quantitySizeLCount = itemRepository.findQuantitySizeLCount(requestDto.getItemId());
                if (arrayOnlyBasketId.contains(requestDto.getKeyIndex())) {
                    Basket basketDuplicate = basketRepository.getById(requestDto.getKeyIndex());
                    if (quantitySizeLCount < requestDto.getItemCount() + basketDuplicate.getItemCount()) {
                        return ResponseEntity.badRequest().body(new MessageResponse(errorMessage));
                    }
                } else if(quantitySizeLCount < requestDto.getItemCount()) {
                    return ResponseEntity.badRequest().body(new MessageResponse(errorMessage));
                }
            }

            if (arrayOnlyBasketId.contains(requestDto.getKeyIndex())) {
                Basket basketDuplicate = basketRepository.getById(requestDto.getKeyIndex());
                basketDuplicate.setItemTotal(basketDuplicate.getItemTotal() + requestDto.getItemTotal());
                basketDuplicate.setItemCount(basketDuplicate.getItemCount() + requestDto.getItemCount());
                continue;
            }
            Basket basket1 = Basket.createBasket(requestDto.getKeyIndex(), userById, itemById, requestDto.getItemCount(), requestDto.getItemTotal(), requestDto.getSize());

            basketRepository.save(basket1);
        }
        return null;
    }

    @Override
    public ResponseEntity<MessageResponse> duplicateSizeQuantityCheck(List<BasketPostReqDto> basketPostReqDto) {
        for (BasketPostReqDto requestDto : basketPostReqDto) {
            Item itemById = itemRepository.getById(requestDto.getItemId());
            String errorMessage = String.format("상품의 수량이 재고수량 보다 많습니다. \n\n제품명 : %s", itemById.getTitle());

            if (requestDto.getSize().equals("S")) {
                int quantitySizeSCount = itemRepository.findQuantitySizeSCount(requestDto.getItemId());
                if (quantitySizeSCount < requestDto.getItemCount()) {
                    return ResponseEntity.badRequest().body(new MessageResponse(errorMessage));
                }
            } else if (requestDto.getSize().equals("M")) {
                int quantitySizeMCount = itemRepository.findQuantitySizeMCount(requestDto.getItemId());
                if (quantitySizeMCount < requestDto.getItemCount()) {
                    return ResponseEntity.badRequest().body(new MessageResponse(errorMessage));
                }
            } else if (requestDto.getSize().equals("L")) {
                int quantitySizeLCount = itemRepository.findQuantitySizeLCount(requestDto.getItemId());
                if (quantitySizeLCount < requestDto.getItemCount()) {
                    return ResponseEntity.badRequest().body(new MessageResponse(errorMessage));
                }
            }
        }
        return null;
    }

    @Transactional
    @Override
    public int getBasketToUserLength(Long userId) {
        User userById = userRepository.getById(userId);
        basketRepository.deleteBasketByUserId(userById);
        List<Basket> basketList = basketRepository.findAllByUserId(userById);
        return basketList.size();
    }


    @Override
    public List<BasketGetResDto> getBasketByUserId(Long userid) {
        User userById = userRepository.getById(userid);
        List<Basket> basketList = basketRepository.findAllByUserId(userById);
        return basketResponseDtos(basketList);
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
        String errorMessage = String.format("상품의 수량이 재고수량 보다 많습니다. \n\n제품명 : %s", itemPrice.getItem().getTitle());
        if (itemPrice.getSize().equals("S")) {
            int quantitySizeSCount = itemRepository.findQuantitySizeSCount(itemPrice.getItem().getId());
            if (quantitySizeSCount <= itemPrice.getItemCount()) {
                return ResponseEntity.badRequest().body(new MessageResponse(errorMessage));
            }
        } else if (itemPrice.getSize().equals("M")) {
            int quantitySizeMCount = itemRepository.findQuantitySizeMCount(itemPrice.getItem().getId());
            if (quantitySizeMCount <= itemPrice.getItemCount()) {
                return ResponseEntity.badRequest().body(new MessageResponse(errorMessage));
            }
        } else if (itemPrice.getSize().equals("L")) {
            int quantitySizeLCount = itemRepository.findQuantitySizeLCount(itemPrice.getItem().getId());
            if (quantitySizeLCount <= itemPrice.getItemCount()) {
                return ResponseEntity.badRequest().body(new MessageResponse(errorMessage));
            }
        }
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
    public ResponseEntity<?> insertNotUserBasket(Long userId, List<BasketPostReqDto> basketDto) {
        User userById = userRepository.getById(userId);
        Basket basket = new Basket();
        for (BasketPostReqDto requestDto : basketDto) {
            Item itemById = itemRepository.getById(requestDto.getItemId());
            Basket duplicateBasket = basketRepository.findItemByIdUserByIdSize(itemById, requestDto.getSize(), userById);
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



    protected List<BasketGetResDto> basketResponseDtos(List<Basket> basketList) {
        return basketList.stream().map(b -> new BasketGetResDto(b)).collect(Collectors.toList());
    }
}
