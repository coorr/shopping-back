package shopping.coor.item.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>,  ItemCustomRepository{
    List<Item> findByIdGreaterThanOrderByIdDesc(Long itemLastId, Pageable pageable);
    List<Item> findByIdLessThanOrderByIdDesc(Long itemLastId,  Pageable pageable);

    List<Item> findByIdLessThanAndCategoryOrderByIdDesc(Long itemLastId, String category, Pageable pageable);
    List<Item> findByIdGreaterThanAndCategoryOrderByIdDesc(Long itemLastId, String category, Pageable pageable);




    @Query("select i from Item i where i.id = ?1")
    Item updateItemOne(Long itemId);

    @Query(value = "SELECT quantitys  FROM item WHERE item_id = ?1", nativeQuery = true)
    int findQuantitySizeSCount(Long item_id);

    @Query(value = "SELECT quantitym  FROM item WHERE item_id = ?1", nativeQuery = true)
    int findQuantitySizeMCount(Long item_id);

    @Query(value = "SELECT quantityl  FROM item WHERE item_id = ?1", nativeQuery = true)
    int findQuantitySizeLCount(Long item_id);


}






























