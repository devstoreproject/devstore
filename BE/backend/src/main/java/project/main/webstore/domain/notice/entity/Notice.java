package project.main.webstore.domain.notice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import project.main.webstore.audit.Auditable;
import project.main.webstore.domain.users.entity.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Notice extends Auditable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(updatable = false)
    private Long id;

    private String title;

    @Lob
    private String content;

    @ElementCollection(fetch = EAGER)
    List<String> imagePathList = new ArrayList<>();
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;
}
