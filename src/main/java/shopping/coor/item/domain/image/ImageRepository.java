package shopping.coor.item.domain.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shopping.coor.item.domain.Item;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    long deleteByIdIn(List<Long> imageId);

    @Modifying(clearAutomatically=true)
    @Query(value = "INSERT INTO image(location,item_id) VALUES (?1, ?2)" , nativeQuery = true)
    void saveImage(String location, Item id);

    @Modifying(clearAutomatically = true)
    @Query("delete from Image i where i.id in (?1)")
    void deleteImage(Set<Long> imageId);

    @Query(value = "SELECT * FROM image WHERE item_id IN (?1);", nativeQuery = true)
    List<Image> getItemToImage(Long itemId);

    Optional<Image> findById(Long id);
}
