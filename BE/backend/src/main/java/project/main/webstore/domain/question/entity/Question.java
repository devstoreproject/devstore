package project.main.webstore.domain.question.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import project.main.webstore.audit.Auditable;
import project.main.webstore.domain.question.enums.QnaStatus;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Question extends Auditable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(updatable = false)
    private Long id;
    private String imagePath;
    private boolean isSecret;

    @Lob
    private String comment;

    @Enumerated
    private QnaStatus qnaStatus = QnaStatus.REGISTER;
}
