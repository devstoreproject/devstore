package project.main.webstore.domain.image.utils;

import org.springframework.stereotype.Component;
import project.main.webstore.domain.image.dto.ImageInfoDto;
import project.main.webstore.domain.image.entity.Image;
import project.main.webstore.exception.BusinessLogicException;
import project.main.webstore.exception.CommonExceptionCode;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ImageUtils {

    public void imageValid(List<ImageInfoDto> imageInfoList) {
        List<ImageInfoDto> checkRepresentative = imageInfoList.stream().filter(info -> info.isRepresentative()).collect(Collectors.toList());
        if (checkRepresentative.size() != 1) {
            throw new BusinessLogicException(CommonExceptionCode.IMAGE_HAS_ALWAYS_REPRESENTATIVE);
        }
        boolean check = imageInfoList.stream().map(ImageInfoDto::getOrder).distinct().count() != imageInfoList.size();
        if (check) {
            throw new BusinessLogicException(CommonExceptionCode.IMAGE_ORDER_ALWAYS_UNIQUE);
        }
    }

    public void changeRepresentativeAndOrder(List<ImageInfoDto> requestInfoList, List<? extends Image> imageList) {
        for (int i = 0; i < requestInfoList.size(); i++) {
            ImageInfoDto requestInfo = requestInfoList.get(i);

            Optional<? extends Image> first = imageList.stream().filter(image -> image.getId() == requestInfo.getId()).findFirst();

            Image image = first.orElseThrow(() -> new BusinessLogicException(CommonExceptionCode.IMAGE_NOT_FOUND));

            image.setRepresentative(requestInfo.isRepresentative());
            image.setImageOrder(requestInfo.getOrder());
        }
    }


    public void deleteImageList(List<? extends Image> imageList, List<Long> deleteId, List<String> deleteImagePath) {
        for (int i = 0; i < deleteId.size(); i++) {
            Long id = deleteId.get(i);
            int index;
            for (index = 0; index < imageList.size(); index++) {
                if (imageList.get(index).getId() == id) {
                    break;
                }
            }
            imageList.remove(index);
            deleteImage(deleteImagePath);
        }
    }

    public void deleteImage(List<String> deletePath) {
        for (String path : deletePath) {
            fileUploader.deleteS3Image(path);
        }
    }
}
