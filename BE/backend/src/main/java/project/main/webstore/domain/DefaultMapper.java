package project.main.webstore.domain;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import project.main.webstore.dto.CustomPage;
import project.main.webstore.dto.Dto;
import project.main.webstore.dto.PageInfo;

public interface DefaultMapper {

    default List<Long> checkListEmpty(List<Long> list) {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    default CustomPage transCustomPage(Page<? extends Dto> pageDate) {
        return new CustomPage<>(pageDate.getContent(),
                new PageInfo(pageDate.getPageable().getOffset(), pageDate.getSize(),
                        pageDate.getTotalElements(), pageDate.isFirst(),
                        pageDate.getNumberOfElements(), pageDate.isFirst(),
                        pageDate.getTotalPages()));

    }

}
