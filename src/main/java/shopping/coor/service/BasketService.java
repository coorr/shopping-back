package shopping.coor.service;

import org.springframework.http.ResponseEntity;
import shopping.coor.repository.basket.dto.BasketRequestDto;
import shopping.coor.repository.basket.dto.BasketResponseDto;
import shopping.coor.repository.user.dto.MessageResponse;

import java.util.List;

public interface BasketService {
    ResponseEntity<MessageResponse> basketAddUser(Long userId, List<BasketRequestDto> basketRequestDto) throws Exception;
    List<BasketResponseDto> getBasketByUserId(Long userid);
    ResponseEntity<?> removeBasketById(Long basketId, Long userId);
    ResponseEntity<?> downBasketUserById(Long basketId, Long userId);
    ResponseEntity<?> plusBasketUserById(Long basketId, Long userId);
    void basketEmpty(Long userId);
    ResponseEntity<?> insertNotUserBasket(Long userId, List<BasketRequestDto> basketDto);
    ResponseEntity<MessageResponse> duplicateSizeQuantityCheck(List<BasketRequestDto> basketRequestDto);
}
