package project.main.webstore.domain.notice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.main.webstore.audit.Auditable;
import project.main.webstore.domain.image.entity.NoticeImage;
import project.main.webstore.domain.notice.enums.NoticeCategory;
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
@AllArgsConstructor
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
    @Enumerated(EnumType.STRING)
    private NoticeCategory noticeCategory;
    //OPERATING("운영"),UPDATE("업데이트"),EVENT("이벤트")
    @Setter
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    public Notice(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public void addReviewImage(NoticeImage image){
        this.noticeImageList.add(image);
        image.setNotice(this);
    }

    public Notice(String title, String content) {
        this.title = title;
        this.content = content;
    }
}