package shopping.coor.order.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.coor.auth.application.service.UserService;
import shopping.coor.auth.domain.User.User;
import shopping.coor.basket.application.service.BasketService;
import shopping.coor.basket.domain.Basket;
import shopping.coor.common.presentation.response.SimpleBooleanResponse;
import shopping.coor.order.domain.OrderRepository;
import shopping.coor.order.presentation.http.request.DeliveryPostReqDto;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceTmp {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final BasketService basketService;

    public SimpleBooleanResponse postOrder(Long userId, DeliveryPostReqDto dto) {
        User user = userService.getUserById(userId);
        List<Basket> basketList = basketService.findBasketByUserId(user);

        return null;
    }
}
