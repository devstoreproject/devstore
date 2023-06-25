package project.main.webstore.domain.qna.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.main.webstore.audit.Auditable;
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
//    @ElementCollection(fetch = EAGER)  이미지 여부 체크할 필요 있음
//    List<String> imagePathList = new ArrayList<>();
    private boolean isSecret;

    @Lob
    private String comment;

    @Enumerated(STRING)
    private QnaStatus qnaStatus = QnaStatus.REGISTER;

    @ManyToOne(fetch = LAZY)
    private User user;

    @OneToOne(fetch = EAGER)
    @JoinColumn(name = "QUESTION_ID")
    private Answer answer;
}
