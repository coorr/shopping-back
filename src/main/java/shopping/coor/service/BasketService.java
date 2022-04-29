package shopping.coor.service;

import org.springframework.http.ResponseEntity;
import shopping.coor.payload.request.BasketRequestDto;

import java.util.List;

public interface BasketService {
    ResponseEntity<?> basketAddUser(Long userId, List<BasketRequestDto> basketRequestDto) throws Exception;

    ResponseEntity<?> getBasketByUserId(Long userid);

    ResponseEntity<?> removeBasketById(Long basketId, Long userId);

    ResponseEntity<?> downBasketUserById(Long basketId, Long userId);

    ResponseEntity<?> plusBasketUserById(Long basketId, Long userId);

    void basketEmpty(Long userId);

    ResponseEntity<?> insertNotUserBasket(Long userId, List<BasketRequestDto> basketDto);
}
