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
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Answer extends Auditable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Enumerated(STRING)
    private QnaStatus qnaStatus;

    @Lob
    private String comment;

    //연관관계 매핑 //
    @OneToOne(fetch = EAGER, mappedBy = "answer")
    private Question question;

    @Setter
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    // 연관관계 편의 메서드 //
    public void addQuestion(Question question){
        this.question = question;
        question.setAnswer(this);
    }

    public Answer(String comment, Long questionId, Long userId) {
        this.qnaStatus = QnaStatus.ANSWER_COMPLETE;
        this.comment = comment;
        this.question = new Question(questionId);
        this.user = new User(userId);
    }
}
