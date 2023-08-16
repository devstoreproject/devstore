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

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Notice extends Auditable {
    @Setter
    @OneToOne(mappedBy = "notice", cascade = CascadeType.ALL)
    NoticeImage noticeImage;
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(updatable = false)
    private Long id;
    @Setter
    private String title;
    @Lob
    @Setter
    private String content;
    private long viewCount;
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

    public Notice(String title, String content,NoticeCategory category) {
        this.title = title;
        this.content = content;
        this.noticeCategory = category;
    }

    public Notice(Long noticeId) {
        this.id = noticeId;
    }

    public void addReviewImage(NoticeImage image) {
        this.noticeImage = image;
        image.setNotice(this);
    }

    public void addViewCount() {
        this.viewCount++;
    }
}