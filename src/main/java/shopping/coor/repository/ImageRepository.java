package shopping.coor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shopping.coor.model.Image;
import shopping.coor.model.Item;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Modifying(clearAutomatically=true)
    @Query(value = "INSERT INTO image(location,item_id) VALUES (?1, ?2)" , nativeQuery = true)
    void saveImage(String location, Item id);
}
