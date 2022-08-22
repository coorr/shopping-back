package shopping.coor.order.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.coor.auth.application.service.UserService;
import shopping.coor.auth.domain.User.User;
import shopping.coor.basket.application.service.BasketService;
import shopping.coor.basket.domain.Basket;
import shopping.coor.common.presentation.response.SimpleBooleanResponse;
import shopping.coor.item.application.exception.NotEnoughStockException;
import shopping.coor.item.application.service.ItemService;
import shopping.coor.item.domain.Item;
import shopping.coor.order.domain.Order;
import shopping.coor.order.domain.OrderRepository;
import shopping.coor.order.domain.delivery.Delivery;
import shopping.coor.order.domain.orderItem.OrderItem;
import shopping.coor.order.presentation.http.request.OrderDeliveryCreateReqDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceTmp {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final BasketService basketService;
    private final ItemService itemService;

    @Transactional
    public SimpleBooleanResponse postOrder(Long userId, OrderDeliveryCreateReqDto dto) {
        User user = userService.getUserById(userId);
        List<Basket> basketList = basketService.findBasketByUserId(user);

        Delivery delivery = dto.toDelivery(dto);

        List<OrderItem> orderItems = new ArrayList<>();
        for (Basket basket : basketList) {
            orderItems.add(OrderItem.createOrderItem(basket));
        }
        Order order = Order.createOrder(user, delivery, orderItems);

        orderRepository.save(order);
        return new SimpleBooleanResponse(Boolean.TRUE);
    }

    public String checkOrder(Long userId) {
        User user = userService.getUserById(userId);
        List<Basket> basketList = basketService.findBasketByUserId(user);

        List<String> soldOutList = new ArrayList<>();
        for (Basket basket : basketList) {
            Item item = itemService.getItemById(basket.getItem().getId());
            soldOutList = itemCountCheck(basket, item, soldOutList);
        }

        if (basketList.size() == soldOutList.size()) {
            throw new NotEnoughStockException();
        }

        String result = soldOutStringStreamJoining(soldOutList);

        return result.length() != 0 ? result : null;
    }

    private String soldOutStringStreamJoining(List<String> soldOutList) {
        return soldOutList.stream()
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    private List<String> itemCountCheck(Basket basket, Item item, List<String> soldOutList) {
        try {
            item.stockCheck(basket.getItemCount(), basket.getSize());
        } catch (NotEnoughStockException e) {
            soldOutList.add(basket.getItem().getTitle() + " " + basket.getSize() + "\n");
        }
        return soldOutList;
    }
}
