package project.main.webstore.domain.image.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.main.webstore.domain.image.dto.ImageInfoDto;
import project.main.webstore.domain.image.entity.Image;
import project.main.webstore.exception.BusinessLogicException;
import project.main.webstore.exception.CommonExceptionCode;
import project.main.webstore.utils.FileUploader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ImageUtils {
    private final FileUploader fileUploader;

    public void imageValid(List<ImageInfoDto> imageInfoList) {
        List<ImageInfoDto> checkRepresentative = imageInfoList.stream().filter(ImageInfoDto::isRepresentative).collect(Collectors.toList());
        if (checkRepresentative.size() != 1) {
            throw new BusinessLogicException(CommonExceptionCode.IMAGE_HAS_ALWAYS_REPRESENTATIVE);
        }
        boolean check = imageInfoList.stream().map(ImageInfoDto::getOrder).distinct().count() != imageInfoList.size();
        if (check) {
            throw new BusinessLogicException(CommonExceptionCode.IMAGE_ORDER_ALWAYS_UNIQUE);
        }
    }

    public List<Image> patchImage(List<ImageInfoDto> infoList, List<? extends Image> imageList, List<Long> deleteIdList) {
        if (infoList.isEmpty() == false) {
            imageValid(infoList);
            List<ImageInfoDto> addImageList = infoList.stream().filter(info -> info.getId() == null).collect(Collectors.toList());
            List<ImageInfoDto> savedImageList = infoList.stream().filter(info -> info.getId() != null).collect(Collectors.toList());

            changeRepresentativeAndOrder(savedImageList, imageList);

            //로직 수정 이후 검증 추가 필요

            if (deleteIdList != null) {
                //사진 삭제하는 경우
                patchDeleteImage(imageList, deleteIdList);
            }
            return fileUploader.uploadImage(addImageList);

        }
        return new ArrayList<>();
    }

    public Image patchImage(ImageInfoDto info, Image image) {
        if (info != null) {
            if (image != null) {
                String imagePath = image.getImagePath();
                fileUploader.deleteS3Image(imagePath);
            }
            return fileUploader.uploadImage(info);
        }
        return null;
    }

    public void deleteImage(List<String> deletePath) {
        for (String path : deletePath) {
            fileUploader.deleteS3Image(path);
        }
    }
    public void deleteImage(Image image){
        fileUploader.deleteS3Image(image.getImagePath());
    }
    private void patchDeleteImage(List<? extends Image> imageList, List<Long> deleteIdList) {
        List<? extends Image> deleteImage = findImageById(deleteIdList, imageList);
        List<String> deleteImagePath = deleteImage.stream().map(Image::getImagePath).collect(Collectors.toList());
        deleteImageList(imageList, deleteIdList, deleteImagePath);
    }

    private List<? extends Image> findImageById(List<Long> deleteImageId, List<? extends Image> imageList) {
        return imageList.stream().filter(image -> deleteImageId.contains(image.getId())).collect(Collectors.toList());
    }

    private void changeRepresentativeAndOrder(List<ImageInfoDto> requestInfoList, List<? extends Image> imageList) {
        for (int i = 0; i < requestInfoList.size(); i++) {
            ImageInfoDto requestInfo = requestInfoList.get(i);

            Optional<? extends Image> first = imageList.stream().filter(image -> image.getId().equals(requestInfo.getId())).findFirst();

            Image image = first.orElseThrow(() -> new BusinessLogicException(CommonExceptionCode.IMAGE_NOT_FOUND));

            image.setRepresentative(requestInfo.isRepresentative());
            image.setImageOrder(requestInfo.getOrder());
        }
    }

    private void deleteImageList(List<? extends Image> imageList, List<Long> deleteId, List<String> deleteImagePath) {
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
}
