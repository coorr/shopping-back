package shopping.coor.item.presentation.http;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shopping.coor.item.application.query.ItemQuery;

@RestController
@RequestMapping(value = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class ItemQueryController {

    private final ItemQuery query;

//    @GetMapping("/items")
//    public ResponseEntity<List<ItemsGetResDto>> getItems(ItemsGetReqDto itemsGetReqDto) {
//        query.
//        return ResponseEntity.ok().body(getItemCommand.getItemsCommand)
//    }

}
