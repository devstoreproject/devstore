package project.main.webstore.dto;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomPage<T> {
    private List<T> content;
    private PageInfo pageable;

    public CustomPage(List<T> content, PageInfo pageable) {
        this.content = content;
        this.pageable = pageable;
    }
}
