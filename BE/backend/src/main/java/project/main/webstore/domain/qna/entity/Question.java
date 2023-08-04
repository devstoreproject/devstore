package project.main.webstore.domain.qna.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.main.webstore.audit.Auditable;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.qna.enums.QnaStatus;
import project.main.webstore.domain.users.entity.User;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.NONE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = PROTECTED)
public class Question extends Auditable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Setter(NONE)
    @Column(updatable = false)
    private Long id;

    @Lob
    private String comment;

    @Enumerated(STRING)
    private QnaStatus qnaStatus = QnaStatus.REGISTER;

    @ManyToOne(fetch = LAZY)
    private User user;

    @OneToOne(fetch = EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "ANSWER_ID")
    private Answer answer;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    public Question(String comment, Long userId,Long itemId) {
        this.item = new Item(itemId);
        this.comment = comment;
        this.user = new User(userId);
    }

    public Question(Long id, String comment, Long userId, Long itemId) {
        this.id = id;
        this.comment = comment;
        this.user = new User(id);
        this.item = new Item(id);
    }

    public Question(Long id) {
        this.id = id;
    }
}