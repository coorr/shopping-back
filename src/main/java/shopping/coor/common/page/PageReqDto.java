package shopping.coor.common.page;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Getter
@Setter
public class PageReqDto {
    private static Integer defaultSize = 5;
    private Integer page;

    private Integer size;

    public Pageable generatePage() {
        return this.generatePage(defaultSize);
    }

    public Pageable generatePage(Integer pageSize) {
        int page = validPage(this.page) ? this.page : 0;
        int size = validSize(this.size) ? this.size : pageSize;
        return PageRequest.of(page, size);
    }

    public Pageable generateCustomSizingPage(Integer pageSize) {
        int page = validPage(this.page) ? this.page : 0;
        return PageRequest.of(page, pageSize);
    }

    private boolean validSize(Integer size) {
        return !(null == size || size < 1);
    }

    private boolean validPage(Integer page) {
        return null != page;
    }
}
