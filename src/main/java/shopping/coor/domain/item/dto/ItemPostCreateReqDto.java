package shopping.coor.domain.item.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemPostCreateReqDto {

    @NotBlank(message = "옷 정보를 입력해주세요.")
    private String title;

    @NotNull(message = "가격을 입력해주세요")
    private int price;

    @NotNull(message = "할인 가격을 입력해주세요")
    private int discountPrice;

    @NotNull(message = "사이즈를 입력해주세요")
    private int quantityS;

    @NotNull(message = "사이즈를 입력해주세요")
    private int quantityM;

    @NotNull(message = "사이즈를 입력해주세요")
    private int quantityL;

    @NotBlank(message = "카테고리를 입력해주세요")
    private String category;

    @NotBlank(message = "입력해주세요")
    private String size;

    @NotBlank(message = "입력해주세요")
    private String material;

    @NotBlank(message = "입력해주세요")
    private String info;
}
