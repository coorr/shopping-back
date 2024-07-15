package shopping.coor.infra.redis;

import org.springframework.data.repository.CrudRepository;

public interface PersonRedisRepository extends CrudRepository<PersonRedis, String> {

}
