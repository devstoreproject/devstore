package project.main.webstore.domain.notice.entity;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.main.webstore.audit.Auditable;
import project.main.webstore.domain.image.entity.NoticeImage;
import project.main.webstore.domain.notice.enums.NoticeCategory;
import project.main.webstore.domain.users.entity.User;

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

    public Notice(Long id, String title, String content,NoticeCategory category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.noticeCategory = category;
    }

    public Notice(String title, String content,NoticeCategory category) {
        this.title = title;
        this.content = content;
        this.noticeCategory = category;
    }

    public Notice(NoticeImage noticeImage, Long id, String title, String content,
            NoticeCategory noticeCategory, User user) {
        this.noticeImage = noticeImage;
        this.id = id;
        this.title = title;
        this.content = content;
        this.noticeCategory = noticeCategory;
        this.user = user;
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