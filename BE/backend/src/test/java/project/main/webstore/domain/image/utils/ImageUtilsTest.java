package project.main.webstore.domain.image.utils;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.main.webstore.domain.image.dto.ImageInfoDto;
import project.main.webstore.domain.image.entity.Image;
import project.main.webstore.exception.BusinessLogicException;
import project.main.webstore.stub.ImageStub;
import project.main.webstore.utils.FileUploader;

@ExtendWith(MockitoExtension.class)
class ImageUtilsTest {
    private final ImageStub stub = new ImageStub();
    @InjectMocks
    private ImageUtils imageUtils;
    @Mock
    private FileUploader fileUploader;

    @Test
    @DisplayName("이미지 검증 오류 : 대표 사진 미설정 or 대표 사진 여러개 설정")
    void imageValid_representative_no_have() throws Exception {
        // given
        List<ImageInfoDto> imageListNoHaveRepresentative = stub.createImageInfoNoRepresentative(1);
        List<ImageInfoDto> imageInfoManyRepresentative = stub.createImageInfoManyRepresentative(1);

        // when
        Throwable exceptionNoRepresentative = catchThrowable(() -> imageUtils.imageValid(imageListNoHaveRepresentative));
        Throwable exceptionManyRepresentative = catchThrowable(() -> imageUtils.imageValid(imageInfoManyRepresentative));

        // then

        Assertions.assertThat(exceptionNoRepresentative).as("대표 사진은 오직 한개가 있어야합니다.")
                .isInstanceOf(BusinessLogicException.class)
                .hasMessage("대표 이미지가 한개 있어야합니다.");

        Assertions.assertThat(exceptionManyRepresentative).as("대표 사진은 오직 한개가 있어야합니다.")
                .isInstanceOf(BusinessLogicException.class)
                .hasMessage("대표 이미지가 한개 있어야합니다.");
    }

    @Test
    @DisplayName("이미지 검증 오류 : 사진 순서 중복")
    void imageValid_order_conflict() throws Exception {
        // given
        List<ImageInfoDto> imageInfo = stub.createImageInfo(0, true);
        // when
        Throwable exceptionImageOrderConflict = catchThrowable(() -> imageUtils.imageValidDTOOrder(imageInfo));

        // then
        Assertions.assertThat(exceptionImageOrderConflict).as("이미지 순서는 중복될 수 없습니다.")
                .isInstanceOf(BusinessLogicException.class)
                .hasMessage("이미지 순서는 중복될 수 없습니다.");
    }

    @Test
    @DisplayName("이미지 수정 검증 : 성공")
    void patchImage_no_imageInfo() throws Exception {
        // given
        List<ImageInfoDto> imageInfo = stub.createImageInfoPatch();//Id 1, 2 존재 나머지 하나는 신규

        List<Image> savedImage = stub.createImageListPatch();
        List<Long> deleteIdList = List.of(1L);

        List<ImageInfoDto> addImageList = imageInfo.stream().filter(info -> info.getId() == null).collect(Collectors.toList());

        given(fileUploader.uploadImage(addImageList)).willReturn(stub.createImageListPatchResult());
        // when
        List<Image> result = imageUtils.patchImage(imageInfo, savedImage, deleteIdList);
        // then
        Assertions.assertThat(result.stream().map(Image::getId).collect(Collectors.toList())).contains(2L, 3L);
        Assertions.assertThat(result.stream().filter(Image::isRepresentative).map(Image::getId).collect(Collectors.toList())).contains(2L);
    }


}