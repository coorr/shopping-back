package shopping.coor.basket.presentation.http;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shopping.coor.auth.application.service.UserDetailsImpl;
import shopping.coor.basket.application.service.BasketServices;
import shopping.coor.basket.presentation.http.request.BasketPostReqDto;
import shopping.coor.basket.presentation.http.response.BasketGetResDto;
import shopping.coor.basket.presentation.http.validator.BasketPostReqDtoValidator;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class BasketControllertmp {

    private final BasketServices basketService;
    private final BasketPostReqDtoValidator basketPostReqDtoValidator;

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
                                                            @RequestBody @Valid  List<BasketPostReqDto> basketPostReqDto) {
        return ResponseEntity.ok().body(basketService.putBaskets(user.getId(),basketPostReqDto));
    }

    @PostMapping("/basket/duplicate")
    public ResponseEntity<Void> checkBasket(@RequestBody @Valid List<BasketPostReqDto> basketPostReqDto) {
        basketService.checkBasket(basketPostReqDto);

        return ResponseEntity.noContent().build();
    }



}
