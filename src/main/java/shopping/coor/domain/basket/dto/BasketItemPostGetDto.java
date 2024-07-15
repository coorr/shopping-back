package shopping.coor.domain.basket.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BasketItemPostGetDto {
    @NotNull(message = "수량을 입력해주세요.")
    private int itemCount;
    @NotBlank(message = "사이지를 입력해주세요.")
    private String size;
}
