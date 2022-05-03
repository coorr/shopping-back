package shopping.coor.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.coor.model.*;
import shopping.coor.repository.basket.dto.BasketResponseDto;
import shopping.coor.repository.delivery.dto.DeliveryRequestDto;
import shopping.coor.repository.item.ItemRepository;
import shopping.coor.repository.user.dto.MessageResponse;
import shopping.coor.repository.basket.BasketRepository;
import shopping.coor.repository.order.OrderRepository;
import shopping.coor.repository.user.UserRepository;
import shopping.coor.service.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final BasketRepository basketRepository;
    private final ItemRepository itemRepository;

    @Transactional
    @Override
    public ResponseEntity<MessageResponse> saveOrderDeliveryItem(Long userId, DeliveryRequestDto deliveryRequestDto) {

        User userById = userRepository.getById(userId);

        List<Basket> basketList = basketRepository.findAllByUserId(userById);

        Delivery delivery = Delivery.createDelivery(deliveryRequestDto.getName(), deliveryRequestDto.getEmail(),
                deliveryRequestDto.getRoadNumber(), deliveryRequestDto.getAddress(), deliveryRequestDto.getDetailText(), deliveryRequestDto.getMessage());

        List<OrderItem> orderItem = new ArrayList<>();
        for (Basket basket : basketList) {
            orderItem.add(OrderItem.createOrderItem((basket.getItem()), basket.getItemTotal(), basket.getItemCount(), basket.getSize()));
        }

        Order order = Order.createOrder(userById, delivery, orderItem);

        orderRepository.save(order);
        basketRepository.deleteBasketByUserId(userById);
        return null;
    }

    @Override
    public ResponseEntity<MessageResponse> quantityCheckOrder(Long userId) {
        User userById = userRepository.getById(userId);
        List<Basket> basketList = basketRepository.findAllByUserId(userById);
        List<String> countOverAry = quantityOverMethod(basketList);
        if (basketList.size() == countOverAry.size()) {
            return ResponseEntity.badRequest().body(new MessageResponse("품절된 상품으로 주문할 수 없습니다."));
        }
        String result = countOverAry.stream().map(n -> String.valueOf(n)).collect(Collectors.joining());

        return result.length() != 0 ? ResponseEntity.accepted().body(new MessageResponse(result)) : null;
    }

    @Transactional
    @Override
    public List<BasketResponseDto> soldOutItemRemove(Long userId) {
        User userById = userRepository.getById(userId);
        List<Basket> basketListQuantity = basketRepository.findAllByUserId(userById);
        for (Basket basket : basketListQuantity) {
            if (basket.getSize().equals("S")) {
                int quantitySizeSCount = itemRepository.findQuantitySizeSCount(basket.getItem().getId());
                if (quantitySizeSCount < basket.getItemCount()) {
                    basketRepository.deleteById(basket.getId());
                }
            } else if (basket.getSize().equals("M")) {
                int quantitySizeMCount = itemRepository.findQuantitySizeMCount(basket.getItem().getId());
                if (quantitySizeMCount < basket.getItemCount()) {
                    basketRepository.deleteById(basket.getId());
                }
            } else if (basket.getSize().equals("L")) {
                int quantitySizeLCount = itemRepository.findQuantitySizeLCount(basket.getItem().getId());
                if (quantitySizeLCount < basket.getItemCount()) {
                    basketRepository.deleteById(basket.getId());
                }
            }
        }

        List<Basket> basketList = basketRepository.findAllByUserId(userById);
        List<BasketResponseDto> result = basketList.stream()
                                            .map(b -> new BasketResponseDto(b))
                                            .collect(Collectors.toList());
        return result;
    }



    private List<String> quantityOverMethod(List<Basket> basketList) {
        List<String> countOverString = new ArrayList<>();
        for (Basket basket : basketList) {
            if (basket.getSize().equals("S")) {
                int quantitySizeSCount = itemRepository.findQuantitySizeSCount(basket.getItem().getId());
                if (quantitySizeSCount < basket.getItemCount()) {
                    countOverString.add(basket.getItem().getTitle()+" "+basket.getSize()+"\n");
                }
            } else if (basket.getSize().equals("M")) {
                int quantitySizeMCount = itemRepository.findQuantitySizeMCount(basket.getItem().getId());
                if (quantitySizeMCount < basket.getItemCount()) {
                    countOverString.add(basket.getItem().getTitle()+" "+basket.getSize()+"\n");
                }
            } else if (basket.getSize().equals("L")) {
                int quantitySizeLCount = itemRepository.findQuantitySizeLCount(basket.getItem().getId());
                if (quantitySizeLCount < basket.getItemCount()) {
                    countOverString.add(basket.getItem().getTitle()+" "+basket.getSize()+"\n");
                }
            }
        }
        return countOverString;
    }


}

















