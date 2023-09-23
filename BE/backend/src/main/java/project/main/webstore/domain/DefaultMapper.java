package project.main.webstore.domain;

import java.util.ArrayList;
import java.util.List;

public interface DefaultMapper {
    default List<Long> checkListEmpty(List<Long> list) {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

}
