package project.main.webstore.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageInfo {
    private Long offset;
    private int size;
    private Long totalElements;
    private boolean last;
    private int numberOfElements;
    private boolean first;
    private int totalPages;

    public PageInfo(Long offset, int size, Long totalElements, boolean last, int numberOfElements,
            boolean first, int totalPages) {
        this.offset = offset;
        this.size = size;
        this.totalElements = totalElements;
        this.last = last;
        this.numberOfElements = numberOfElements;
        this.first = first;
        this.totalPages = totalPages;
    }
}

