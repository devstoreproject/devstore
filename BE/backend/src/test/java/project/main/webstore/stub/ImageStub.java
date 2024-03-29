package project.main.webstore.stub;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import project.main.webstore.domain.image.dto.ImageInfoDto;
import project.main.webstore.domain.image.entity.Image;

import java.io.IOException;
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

    public ImageStub() {
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
        for (int i = 1; i <= idx; i++) {
            boolean representative = i == 1;
            list.add(createImage((long) i, i, representative));
        }
        return list;
    }

    public List<Image> createImageListPatch() {
        List<Image> list = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            boolean representative = i == 1;
            list.add(createImage((long) i, i, representative));
        }
        return list;
    }

    public List<Image> createImageListPatchResult() {
        return List.of(
                createImage(2L, 1, true),
                createImage(3L, 3, false)
        );
    }


    public ImageInfoDto createImageInfoDto(int order, boolean target) {
        return ImageInfoDto.dtoBuilder()
                .uploadDir("review")
                .representative(target)
                .order(order)
                .multipartFile(mockMultipartFile)
                .build();
    }

    public ImageInfoDto createImageInfoPath(Long id, int order, boolean target) {
        return ImageInfoDto.dtoBuilder()
                .uploadDir("review")
                .representative(target)
                .order(order)
                .multipartFile(mockMultipartFile)
                .id(id)
                .build();
    }
    public ImageInfoDto createImageInfoPath(int order, boolean target) {
        return ImageInfoDto.dtoBuilder()
                .uploadDir("review")
                .representative(target)
                .order(order)
                .multipartFile(mockMultipartFile)
                .build();
    }

    public List<ImageInfoDto> createImageInfo(int order, boolean flag) {
        return Stream.of(
                createImageInfoDto(0, false),
                createImageInfoDto(order, flag)

        ).collect(Collectors.toList());
    }

    public List<ImageInfoDto> createImageInfoNoRepresentative(int order) {
        return Stream.of(
                createImageInfoDto(0, false),
                createImageInfoDto(order, false)

        ).collect(Collectors.toList());
    }

    public List<ImageInfoDto> createImageInfoManyRepresentative(int order) {
        return Stream.of(
                createImageInfoDto(0, true),
                createImageInfoDto(order, true)

        ).collect(Collectors.toList());
    }


    public List<ImageInfoDto> createImageInfoPatch() {
        return List.of(
                createImageInfoPath(1L, 2, false),
                createImageInfoPath(2L, 1, true),
                createImageInfoPath(null, 3, false)
        );
    }

    public ByteArrayResource getRealImage() throws IOException {
        Resource resource = new ClassPathResource("image/testImage.png");

        ByteArrayResource bytes = new ByteArrayResource(resource.getInputStream().readAllBytes()){
            public String getFilename() {
                return "image.png";
            }
        };
        return bytes;
    }

}