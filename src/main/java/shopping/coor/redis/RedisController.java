package shopping.coor.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RedisController {

    private final RedisTemplate<String, String> redisTemplate;
    private final PersonRedisRepository personRedisRepository;

    @PostMapping("/api/user/redisTest")
    public ResponseEntity<?> addRedisKey() {
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        vop.set("1", "abc");
        vop.set("2", "def");
        vop.set("2", "ghi");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
