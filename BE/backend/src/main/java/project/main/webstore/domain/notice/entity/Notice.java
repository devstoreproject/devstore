package project.main.webstore.domain.notice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.main.webstore.audit.Auditable;
import project.main.webstore.domain.image.entity.NoticeImage;
import project.main.webstore.domain.users.entity.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @Setter
    @OneToMany(mappedBy = "notice",cascade = CascadeType.ALL,orphanRemoval = true)
    List<NoticeImage> noticeImageList = new ArrayList<>();
    @Setter
    private String title;
    @Lob
    @Setter
    private String content;
    @Setter
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    public void addReviewImage(NoticeImage image){
        this.noticeImageList.add(image);
        image.setNotice(this);
    }
}
