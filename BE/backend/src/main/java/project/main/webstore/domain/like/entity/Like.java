package project.main.webstore.domain.like.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import project.main.webstore.audit.Auditable;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.users.entity.User;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "Likes")
@NoArgsConstructor(access = PROTECTED)
public class Like extends Auditable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(updatable = false)
    private Long id;
    private int count;

    //연관관계 매핑
    // item
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;
    //user
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

}
