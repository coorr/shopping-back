package shopping.coor.infrastructure.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("person")
public class PersonRedis {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private AddressRedis address;

}
