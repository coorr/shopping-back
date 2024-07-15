package shopping.coor.domain.basket;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shopping.coor.domain.user.UserDetailsImpl;
import shopping.coor.domain.basket.dto.BasketItemPostGetDto;
import shopping.coor.domain.basket.dto.BasketPatchCountReqDto;
import shopping.coor.domain.basket.dto.BasketPostReqDto;
import shopping.coor.domain.basket.dto.BasketPutReqDto;
import shopping.coor.domain.basket.dto.BasketGetResDto;
import shopping.coor.domain.basket.enums.BasketOrder;
import shopping.coor.domain.basket.validator.BasketPatchCountReqDtoValidator;
import shopping.coor.common.exception.ValidationIllegalArgumentException;
import shopping.coor.common.container.SimpleBooleanResponse;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;
    private final BasketPatchCountReqDtoValidator basketPatchCountReqDtoValidator;

    @GetMapping("/baskets")
    public ResponseEntity<List<BasketGetResDto>> getBaskets(@AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.ok().body(basketService.getBaskets(user.getId()));
    }

    // 회원 장바구니 추가
    @PostMapping("/baskets")
    public ResponseEntity<Long> postBaskets(@AuthenticationPrincipal UserDetailsImpl user,
                                           @RequestBody @Valid  List<BasketPostReqDto> basketPostReqDto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(basketService.postBaskets(user.getId(), basketPostReqDto));
    }

    // 비회원 장바구니 추가 및 변경
    @PutMapping("/baskets")
    public ResponseEntity<List<BasketGetResDto>> putBaskets(@AuthenticationPrincipal UserDetailsImpl user,
                                                            @RequestBody @Valid  List<BasketPutReqDto> basketPutReqDto) {
        return ResponseEntity.ok().body(basketService.putBaskets(user.getId(), basketPutReqDto));
    }

    // 아이템 상세페이지에서 수량체크
    @PostMapping("/basket/check")
    public ResponseEntity<Void> checkBasket(@RequestBody @Valid List<BasketPostReqDto> basketPostReqDto) {
        basketService.checkBasket(basketPostReqDto);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/basket/{itemId}")
    public ResponseEntity<SimpleBooleanResponse> checkItem(@PathVariable Long itemId,
                                          @RequestBody @Valid BasketItemPostGetDto dto) {

        return ResponseEntity.ok().body(basketService.checkItem(itemId, dto));
    }

    @DeleteMapping("/basket/{basketId}")
    public ResponseEntity<SimpleBooleanResponse> deleteBasket(@PathVariable Long basketId) {
        return ResponseEntity.ok().body(basketService.deleteBasket(basketId));
    }

    @PatchMapping("/basket/{basketId}")
    public ResponseEntity<List<BasketGetResDto>> patchBasketCount(@PathVariable Long basketId,
                                                                  @RequestBody BasketPatchCountReqDto dto,
                                                                  BindingResult bindingResult,
                                                                  @AuthenticationPrincipal UserDetailsImpl user) {
        basketPatchCountReqDtoValidator.validateTarget(dto, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new ValidationIllegalArgumentException(bindingResult);
        }

        return ResponseEntity.ok().body(basketService.updateCount(basketId, user.getId(), BasketOrder.valueOf(dto.getOrder())));
    }

    @DeleteMapping("/baskets")
    public ResponseEntity<SimpleBooleanResponse> deleteAllBasket(@AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.ok().body(basketService.deleteAllBasket(user.getId()));
    }



}
