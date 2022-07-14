package shopping.coor.item.presentation.http;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shopping.coor.item.application.command.GetItemCommand;
import shopping.coor.item.presentation.http.request.ItemsGetReqDto;
import shopping.coor.item.presentation.http.response.ItemGetResDto;
import shopping.coor.item.presentation.http.response.ItemsGetResDto;

import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class ItemCommandController {

    private final GetItemCommand getItemCommand;

    //    @GetMapping("/{itemId}")
    @GetMapping("/item/getItemOne/{itemId}")
    public ResponseEntity<ItemGetResDto> getItem(@PathVariable Long itemId) {
        return ResponseEntity.ok().body(getItemCommand.getItemCommand(itemId));
    }


}
