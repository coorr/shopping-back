package shopping.coor.domain.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query(value = "SELECT quantitys  FROM item WHERE id = ?1", nativeQuery = true)
    int findQuantitySizeSCount(Long itemId);

    @Query(value = "SELECT quantitym  FROM item WHERE id = ?1", nativeQuery = true)
    int findQuantitySizeMCount(Long itemId);

    @Query(value = "SELECT quantityl  FROM item WHERE id = ?1", nativeQuery = true)
    int findQuantitySizeLCount(Long itemId);
}






























