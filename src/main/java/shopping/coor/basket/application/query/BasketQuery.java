package shopping.coor.basket.application.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasketQuery {

    private final BasketSupport support;

    public void getBaskets(final Long userId) {

    }
}
