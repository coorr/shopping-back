package shopping.coor.item.application.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopping.coor.item.presentation.http.request.ItemGetListReqDto;

@Service
@RequiredArgsConstructor
public class ItemQuery  {
    private final ItemSupport support;

    public void getItems(ItemGetListReqDto request) {


    }
}
