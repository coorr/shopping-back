package shopping.coor.infra.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddressRedis {

    private final String city;
    private final String country;

}
