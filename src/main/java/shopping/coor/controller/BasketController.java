package shopping.coor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shopping.coor.repository.basket.dto.BasketRequestDto;
import shopping.coor.repository.basket.dto.BasketResponseDto;
import shopping.coor.repository.user.dto.MessageResponse;
import shopping.coor.service.BasketService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/basket")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;

    @PostMapping("/basketAddUser/{userId}")
    public ResponseEntity<MessageResponse> basketAddUser(@PathVariable Long userId, @RequestBody List<BasketRequestDto> basketRequestDto) throws Exception {
        return basketService.basketAddUser(userId, basketRequestDto);
    }
    @GetMapping("/getBasketByUserId/{userid}")
    public List<BasketResponseDto> getBasketByUserId(@PathVariable Long userid) {
        return basketService.getBasketByUserId(userid);
    }
    @PostMapping("/removeBasketById/{basketId}/{userId}")
    public ResponseEntity<?> removeBasketById(@PathVariable Long basketId, @PathVariable Long userId) {
        return basketService.removeBasketById(basketId, userId);
    }
    @PostMapping("/basketDownUserById/{basketId}/{userId}")
    public ResponseEntity<?> downBasketUserById(@PathVariable Long basketId, @PathVariable Long userId) {
        return basketService.downBasketUserById(basketId, userId);
    }
    @PostMapping("/basketPlusUserById/{basketId}/{userId}")
    public ResponseEntity<?> plusBasketUserById(@PathVariable Long basketId, @PathVariable Long userId) {
        return basketService.plusBasketUserById(basketId, userId);
    }
    @PostMapping("/basketEmpty/{userId}")
    public void basketEmpty(@PathVariable Long userId) {
         basketService.basketEmpty(userId);
    }

    @PostMapping("/insertNotUserBasket/{userId}")
    public ResponseEntity<?> insertNotUserBasket(@PathVariable Long userId, @RequestBody List<BasketRequestDto> basketDto) {
        return basketService.insertNotUserBasket(userId, basketDto);
    }
    @PostMapping("/duplicateSizeQuantityCheck")
    public ResponseEntity<?> duplicateSizeQuantityCheck(@RequestBody List<BasketRequestDto> basketDto) {
        return basketService.duplicateSizeQuantityCheck(basketDto);
    }
    @GetMapping("/getBasketToUserLength/{userId}")
    public int getBasketToUserLength(@PathVariable Long userId) {
        return basketService.getBasketToUserLength(userId);
    }

}




















