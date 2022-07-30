package shopping.coor.service;

import org.springframework.http.ResponseEntity;
import shopping.coor.basket.presentation.http.request.BasketPostReqDto;
import shopping.coor.basket.presentation.http.response.BasketGetResDto;
import shopping.coor.auth.presentation.http.request.MessageResponse;

import java.util.List;

public interface BasketService {
    ResponseEntity<MessageResponse> basketAddUser(Long userId, List<BasketPostReqDto> basketPostReqDto) throws Exception;
    List<BasketGetResDto> getBasketByUserId(Long userid);
    ResponseEntity<?> removeBasketById(Long basketId, Long userId);
    ResponseEntity<?> downBasketUserById(Long basketId, Long userId);
    ResponseEntity<?> plusBasketUserById(Long basketId, Long userId);
    void basketEmpty(Long userId);
    ResponseEntity<?> insertNotUserBasket(Long userId, List<BasketPostReqDto> basketDto);
    ResponseEntity<MessageResponse> duplicateSizeQuantityCheck(List<BasketPostReqDto> basketPostReqDto);
    int getBasketToUserLength(Long userId);
}
