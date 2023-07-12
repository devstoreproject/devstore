package project.main.webstore.domain.item.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import project.main.webstore.domain.users.entity.User;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class PickedItem {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(updatable = false)
    private Long pickedId;

    @OneToMany(mappedBy = "pickedItem")

    //연관관계매핑
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name ="USER_ID")
    private User user;
}
