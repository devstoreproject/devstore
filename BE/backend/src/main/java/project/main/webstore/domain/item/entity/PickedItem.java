package project.main.webstore.domain.item.entity;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.main.webstore.domain.users.entity.User;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class PickedItem {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(updatable = false)
    private Long pickedId;

    @ManyToOne
    @JoinColumn(name ="ITEM_ID")
    private Item item;
    //연관관계매핑
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name ="USER_ID")
    private User user;

    public PickedItem(Item item, User user) {
        this.item = item;
        this.user = user;
    }
}
