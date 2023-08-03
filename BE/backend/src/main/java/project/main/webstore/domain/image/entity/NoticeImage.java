package project.main.webstore.domain.image.entity;


import lombok.NoArgsConstructor;
import lombok.Setter;
import project.main.webstore.domain.notice.entity.Notice;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("NOTICE")
@NoArgsConstructor
public class NoticeImage extends Image {
    @Setter
    @OneToOne
    @JoinColumn(name = "NOTICE_ID")
    private Notice notice;

    public NoticeImage(Image image, Notice notice) {
        super(image.getId() != null ? image.getId():null,image.getOriginalName(), image.getUploadName(), image.getImagePath(), image.getExt(), image.getThumbnailPath(), image.getImageOrder(), image.isRepresentative(),image.getHash());
        this.notice = notice;
    }
}
