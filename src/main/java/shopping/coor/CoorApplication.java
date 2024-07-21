package shopping.coor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class CoorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoorApplication.class, args);
	}
}
