package project.main.webstore.domain.image.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import project.main.webstore.domain.image.dto.ImageDto;
import project.main.webstore.domain.image.dto.ImageInfoDto;
import project.main.webstore.domain.image.dto.ImageSortDto;
import project.main.webstore.domain.image.entity.Image;
import project.main.webstore.exception.BusinessLogicException;
import project.main.webstore.exception.CommonExceptionCode;

@Component
public class ImageMapper {

    public ImageInfoDto toLocalDto(MultipartFile file, ImageSortDto sortDto, String uploadDir) {
        return ImageInfoDto.dtoBuilder()
                .multipartFile(file)
                .order(sortDto.getOrderNumber())
                .representative(sortDto.isRepresentative())
                .id(sortDto.findImageId())
                .uploadDir(uploadDir)
                .build();
    }

    public ImageInfoDto toLocalDto(ImageSortDto sortDto) {
        return new ImageInfoDto(sortDto.findImageId(), sortDto.getOrderNumber(),
                sortDto.isRepresentative());
    }

    public List<ImageInfoDto> toLocalDtoList(List<MultipartFile> fileList,
            List<? extends ImageSortDto> imageSortDto, String uploadDir) {
        checkImageParam(fileList, imageSortDto);
        List<ImageInfoDto> result = new ArrayList<>();
        int j = 0;
        for (int i = 0; i < imageSortDto.size(); i++) {
            ImageInfoDto infoDto;
            if (imageSortDto.get(i).findImageId() == null) {
                infoDto = toLocalDto(fileList.get(j++), imageSortDto.get(i), uploadDir);

            } else {
                infoDto = toLocalDto(imageSortDto.get(i));
            }

            result.add(infoDto);
        }
        return result;
    }

    public ImageInfoDto toLocalDto(MultipartFile file, String uploadDir) {
        return ImageInfoDto.dtoBuilder()
                .multipartFile(file)
                .order(1)
                .representative(true)
                .id(null)
                .uploadDir(uploadDir)
                .build();
    }

    private void checkImageParam(List<MultipartFile> imageList,
            List<? extends ImageSortDto> infoList) {
        if (imageList == null && infoList != null) {
            throw new BusinessLogicException(CommonExceptionCode.IMAGE_NOT_POST);
        }
        if (imageList != null && infoList == null) {
            throw new BusinessLogicException(CommonExceptionCode.IMAGE_INFO_NOT_POST);
        }
    }

    public List<ImageDto> toImageResponseDtoList(List<? extends Image> imageList) {
        return imageList.stream().map(ImageDto::new).collect(Collectors.toList());
    }
}
