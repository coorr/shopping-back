package shopping.coor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shopping.coor.payload.request.BasketRequestDto;
import shopping.coor.service.BasketService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/basket")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;

    @PostMapping("/basketAddUser/{userId}")
    public ResponseEntity<?> basketAddUser(@PathVariable Long userId, @RequestBody List<BasketRequestDto> basketRequestDto) throws Exception {
        return basketService.basketAddUser(userId, basketRequestDto);
    }
    @GetMapping("/getBasketByUserId/{userid}")
    public ResponseEntity<?> getBasketByUserId(@PathVariable Long userid) {
        return basketService.getBasketByUserId(userid);
    }
    @PostMapping("/removeBasketById/{basketId}/{userId}")
    public ResponseEntity<?> removeBasketById(@PathVariable Long basketId, @PathVariable Long userId) {
        return basketService.removeBasketById(basketId, userId);
    }
    @PostMapping("/basketDownUserById/{basketId}/{userId}")
    public ResponseEntity<?> basketDownUserById(@PathVariable Long basketId, @PathVariable Long userId) {
        return basketService.basketDownUserById(basketId, userId);
    }
    @PostMapping("/basketPlusUserById/{basketId}/{userId}")
    public ResponseEntity<?> basketPlusUserById(@PathVariable Long basketId, @PathVariable Long userId) {
        return basketService.basketPlusUserById(basketId, userId);
    }
    @PostMapping("/basketEmpty/{userId}")
    public void basketEmpty(@PathVariable Long userId) {
         basketService.basketEmpty(userId);
    }
}




















