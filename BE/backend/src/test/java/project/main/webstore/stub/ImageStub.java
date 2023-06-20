package project.main.webstore.stub;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import project.main.webstore.domain.image.dto.ImageInfoDto;
import project.main.webstore.domain.image.entity.Image;
import project.main.webstore.domain.image.entity.ReviewImage;
import project.main.webstore.domain.review.entity.Review;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ImageStub {
    private String fileName = "testImage";
    private String ext = "png";
    private String path = "src/test/resources/image/testImage";
    private byte[] content = "This is Mock Image".getBytes();

    private MockMultipartFile mockMultipartFile;
    private MockMultipartFile mockMultipartFile2;
    private List<MultipartFile> fileList = new ArrayList<>();

    public ImageStub(){
            fileList = new ArrayList<>();
            mockMultipartFile = new MockMultipartFile(fileName, fileName.concat(".").concat(ext), ext, content);
            mockMultipartFile2 = new MockMultipartFile(fileName, fileName.concat(".").concat(ext), ext, content);
            fileList.add(mockMultipartFile);
            fileList.add(mockMultipartFile2);

    }

    public Image createImage(int order, boolean representative) {
        return new Image(fileName + order, "업로드 네임", path + order + "." + ext, ext, path + order + "." + ext, order, representative, "해쉬 코드가 들어올 자리");
    }

    public Image createImage(Long id, int order, boolean representative) {
        return new Image(id, fileName + order, "업로드 네임", path + order + "." + ext, ext, path + order + "." + ext, order, representative, "해쉬 코드가 들어올 자리");
    }

    public List<Image> createImageList(int idx) {
        List<Image> list = new ArrayList<>();
        for (int i = 0; i < idx; i++) {
            boolean representative = i == 1;
            list.add(createImage((long) i, i, representative));
        }
        return list;
    }

    public List<ReviewImage> createReviewImage(Review review) {
        List<ReviewImage> reviewImageList = createImageList(2).stream().map(image -> new ReviewImage(image, review)).collect(Collectors.toList());
        review.setReviewImageList(reviewImageList);
        return reviewImageList;
    }

    public ImageInfoDto createImageInfoDto(int order, boolean target) {
        return ImageInfoDto.dtoBuilder()
                .uploadDir("review")
                .representative(target)
                .order(order)
                .multipartFile(mockMultipartFile)
                .build();
    }

    public List<ImageInfoDto> createImageInfoList(int order, boolean target) {
        return Stream.of(
                createImageInfoDto(0, false),
                createImageInfoDto(order, target)

        ).collect(Collectors.toList());
    }
}