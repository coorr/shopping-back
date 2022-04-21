package shopping.coor.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shopping.coor.model.Item;
import shopping.coor.payload.request.ItemRequestDto;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("select distinct i from Item i where i.id < ?1  order by i.id desc ")
    List<Item> getItem(Long lastId, PageRequest pageRequest);

    @Query("select distinct i from Item i where i.id > ?1  order by i.id desc ")
    List<Item> getItemFirst(Long lastId, PageRequest pageRequest);
}
