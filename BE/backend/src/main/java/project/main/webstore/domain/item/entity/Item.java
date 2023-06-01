package project.main.webstore.domain.item.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import project.main.webstore.domain.item.enums.Category;
import project.main.webstore.domain.item.enums.ItemStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "id")})
@NoArgsConstructor(access = PROTECTED)
public class Item {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(updatable = false)
    private Long id;
    private String name;
    private int itemCount;
    private int price;
    private Category category;

    @Lob
    private String description;
    //상세 정보

    @Enumerated
    private ItemStatus itemStatus = ItemStatus.ON_STACK;

    @OneToMany(mappedBy = "item")
    private List<Spec> specList = new ArrayList<>();
}
