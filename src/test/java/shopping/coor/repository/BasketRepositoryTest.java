package shopping.coor.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shopping.coor.repository.basket.BasketRepository;

@DataJpaTest
class BasketRepositoryTest {

    @Autowired
    BasketRepository basketRepository;
}