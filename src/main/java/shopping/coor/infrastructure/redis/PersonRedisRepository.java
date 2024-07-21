package shopping.coor.infrastructure.redis;

import org.springframework.data.repository.CrudRepository;

public interface PersonRedisRepository extends CrudRepository<PersonRedis, String> {

}
