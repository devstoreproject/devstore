package project.main.webstore.stub;

import project.main.webstore.domain.image.entity.Image;
import project.main.webstore.domain.image.entity.ReviewImage;
import project.main.webstore.domain.review.entity.Review;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ImageStub {
    private String fileName = "testImage";
    private String ext = "png";
    private String path = "src/test/resources/image/testImage";

    public Image createImage(int order, boolean representative) {
        return new Image(fileName + order, "업로드 네임", path + order + "." + ext, ext, path + order + "." + ext, order, representative, "해쉬 코드가 들어올 자리");
    }
    public Image createImage(Long id,int order, boolean representative) {
        return new Image(id,fileName + order, "업로드 네임", path + order + "." + ext, ext, path + order + "." + ext, order, representative, "해쉬 코드가 들어올 자리");
    }

    public List<Image> createImageList(int idx) {
        List<Image> list = new ArrayList<>();
        for (int i = 0; i < idx; i++) {
            boolean representative = i == 1;
            list.add(createImage((long)i,i, representative));
        }
        return list;
    }


    public List<ReviewImage> createReviewImage(Review review) {
        List<ReviewImage> reviewImageList = createImageList(2).stream().map(image -> new ReviewImage(image, review)).collect(Collectors.toList());
        review.setReviewImageList(reviewImageList);
        return reviewImageList;
    }
}