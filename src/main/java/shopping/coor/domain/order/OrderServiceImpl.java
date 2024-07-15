package shopping.coor.domain.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.coor.domain.user.User;
import shopping.coor.domain.basket.Basket;
import shopping.coor.domain.basket.dto.BasketGetResDto;
import shopping.coor.domain.order.enums.OrderStatus;
import shopping.coor.domain.delivery.Delivery;
import shopping.coor.domain.delivery.DeliveryStatus;
import shopping.coor.domain.order.item.OrderItem;
import shopping.coor.domain.order.dto.OrderDeliveryPostReqDto;
import shopping.coor.domain.item.ItemRepository;
import shopping.coor.domain.order.dto.OrderResponseDto;
import shopping.coor.common.container.MessageResponse;
import shopping.coor.domain.basket.BasketRepository;
import shopping.coor.domain.user.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public ResponseEntity<MessageResponse> saveOrderDeliveryItem(Long userId, OrderDeliveryPostReqDto orderDeliveryPostReqDto) {
        User userById = userRepository.getById(userId);
        List<Basket> basketList = basketRepository.findAllByUserId(userById);

        Delivery delivery = Delivery.createDelivery(orderDeliveryPostReqDto.getName(), orderDeliveryPostReqDto.getEmail(),
                orderDeliveryPostReqDto.getRoadNumber(), orderDeliveryPostReqDto.getAddress(), orderDeliveryPostReqDto.getDetailText(), orderDeliveryPostReqDto.getMessage());

        List<OrderItem> orderItem = new ArrayList<>();
        for (Basket basket : basketList) {
            orderItem.add(OrderItem.createOrderItem(basket));
        }
        Order order = Order.createOrder(userById, delivery, orderItem);

        orderRepository.save(order);
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
        String result = countOverAry.stream()
                .map(n -> String.valueOf(n))
                .collect(Collectors.joining());

        return result.length() != 0 ? ResponseEntity.accepted().body(new MessageResponse(result)) : null;
    }

    @Transactional
    @Override
    public List<BasketGetResDto> soldOutItemRemove(Long userId) {
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
        List<BasketGetResDto> result = basketList.stream()
                                            .map(b -> new BasketGetResDto(b))
                                            .collect(Collectors.toList());
        return result;
    }

    @Override
    public List<OrderResponseDto> getOrderUserById(Long userId, String startDate, String endDate, String status) {
        User userById = userRepository.getById(userId);
        LocalDateTime startTime = stringToLocalDateTime(startDate);
        LocalDateTime endTime = stringToLocalDateTime(endDate);
        if (status.equals("ORDER")) {
            List<Order> orderList = orderRepository.getOrderStatusUserById(userById, startTime, endTime, OrderStatus.ORDER);
            List<OrderResponseDto> result = orderList.stream().map(o -> new OrderResponseDto(o)).collect(Collectors.toList());
            return result;
        }
        if (status.equals("CANCEL")) {
            List<Order> orderList = orderRepository.getOrderStatusUserById(userById, startTime, endTime, OrderStatus.CANCEL);
            List<OrderResponseDto> result = orderList.stream().map(o -> new OrderResponseDto(o)).collect(Collectors.toList());
            return result;
        }
        if (status.equals("READY")) {
            List<Order> orderList = orderRepository.getOrderDeliverStatusUserById(userById, startTime, endTime, DeliveryStatus.READY);
            List<OrderResponseDto> result = orderList.stream().map(o -> new OrderResponseDto(o)).collect(Collectors.toList());
            return result;
        }
        if (status.equals("COMP")) {
            List<Order> orderList = orderRepository.getOrderDeliverStatusUserById(userById, startTime, endTime, DeliveryStatus.COMP);
            List<OrderResponseDto> result = orderList.stream().map(o -> new OrderResponseDto(o)).collect(Collectors.toList());
            return result;
        }
        List<Order> orderList = orderRepository.getOrderUserById(userById, startTime, endTime);
        List<OrderResponseDto> result = orderList.stream().map(o -> new OrderResponseDto(o)).collect(Collectors.toList());
        return result;
    }

    @Transactional
    @Override
    public List<OrderResponseDto> cancelOrderItem(Long orderId, String startDate, String endDate) {
        Order order = orderRepository.getById(orderId);
        order.cancel();
        LocalDateTime startTime = stringToLocalDateTime(startDate);
        LocalDateTime endTime = stringToLocalDateTime(endDate);
        List<Order> orderList = orderRepository.getOrderUserById(order.getUser(), startTime, endTime);
        List<OrderResponseDto> result = orderList.stream().map(o -> new OrderResponseDto(o)).collect(Collectors.toList());

        return result;
    }

    private LocalDateTime stringToLocalDateTime(String time) {
        DateTimeFormatter form = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime startDateTimeChange= LocalDateTime.parse(time.substring(0,14),form);
        String stringStartDate = startDateTimeChange.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(stringStartDate,formatter);
        return localDateTime;
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

















