package project.main.webstore.domain.image.entity;

import lombok.NoArgsConstructor;
import lombok.Setter;
import project.main.webstore.domain.review.entity.Review;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static javax.persistence.FetchType.LAZY;

@Entity
@DiscriminatorValue("REVIEW")
@NoArgsConstructor
public class ReviewImage extends Image{
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "REVIEW_ID")
    @Setter
    private Review review;

    public ReviewImage(Image image, Review review) {
        super(image.getRealTitle(), image.getSavedTitle(), image.getImagePath(), image.getExt(), image.getThumbnailPath(), image.getImageOrder(), image.isRepresentative());
        this.review = review;
    }
}
