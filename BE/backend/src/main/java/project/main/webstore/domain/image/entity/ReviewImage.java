package project.main.webstore.domain.image.entity;

import project.main.webstore.domain.review.entity.Review;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@DiscriminatorValue("REVIEW")
public class ReviewImage extends Image{
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "REVIEW_ID")
    private Review review;
}
