package shopping.coor.basket.presentation.http;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shopping.coor.basket.application.query.BasketQuery;

@RestController
@RequestMapping(value = "/api/basket", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class BasketQueryController {

    private final BasketQuery basketQuery;

//    @GetMapping("/{userId}")
//    public ResponseEntity<List<BasketRequestDto>> getBaskets(@PathVariable Long userId) {
//        return ResponseEntity.ok().body(basketQuery.getBaskets(userId));
//    }
}
