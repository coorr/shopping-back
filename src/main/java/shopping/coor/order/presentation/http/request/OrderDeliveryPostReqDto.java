package shopping.coor.order.presentation.http.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class OrderDeliveryPostReqDto {

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;
    @Email(message = "이메일을 입력해주세요.")
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;
    @NotNull(message = "우편번호를 입력해주세요.")
    @Size(min = 0, max = 5, message = "우편번호는 0-5 사이여야 합니다.")
    private int roadNumber;
    @NotBlank(message = "주소를 입력해주세요.")
    private String address;
    @NotBlank(message = "주소를 입력해주세요.")
    private String detailText;
    private String message;

}
