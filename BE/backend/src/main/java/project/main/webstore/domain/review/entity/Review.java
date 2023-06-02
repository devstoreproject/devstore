package project.main.webstore.domain.review.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import project.main.webstore.audit.Auditable;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.users.entity.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Review extends Auditable {
    @ElementCollection
    List<String> imagePathList = new ArrayList<>();
    @Id
    @GeneratedValue
    @Column(unique = true, updatable = false)
    private Long id;
    private String comment;
    private int rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;
}
