package shopping.coor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shopping.coor.model.Image;
import shopping.coor.model.Item;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

//    @Query("select i from Item i")
//    List<Item> getItem();
}
