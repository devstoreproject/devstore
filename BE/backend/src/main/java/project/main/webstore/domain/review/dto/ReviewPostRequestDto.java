package project.main.webstore.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.users.entity.User;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class ReviewPostRequestDto {
    private Long userId;
    private String comment;
    private int rating;
    List<String> imagePathList = new ArrayList<>();
}
