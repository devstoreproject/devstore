package project.main.webstore.domain.question.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import project.main.webstore.audit.Auditable;
import project.main.webstore.domain.question.enums.QnaStatus;
import project.main.webstore.domain.users.entity.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;
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
    @ElementCollection(fetch = EAGER)
    List<String> imagePathList = new ArrayList<>();
    private boolean isSecret;

    @Lob
    private String comment;

    @Enumerated(STRING)
    private QnaStatus qnaStatus = QnaStatus.REGISTER;

    @ManyToOne(fetch = LAZY)
    private User user;
}
