package shopping.coor.basket.presentation.http;


import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/basket", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class BasketCommandController {
}
