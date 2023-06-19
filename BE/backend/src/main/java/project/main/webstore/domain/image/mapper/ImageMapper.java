package project.main.webstore.domain.image.mapper;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import project.main.webstore.domain.image.dto.ImageInfoDto;
import project.main.webstore.domain.image.dto.ImageSortDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class ImageMapper {
    public ImageInfoDto toLocalDto(MultipartFile file, ImageSortDto sortDto, String uploadDir){
        return ImageInfoDto.dtoBuilder()
                .multipartFile(file)
                .order(sortDto.getOrderNumber())
                .representative(sortDto.isRepresentative())
                .id(sortDto.getId())
                .uploadDir(uploadDir)
                .build();
    }
    public List<ImageInfoDto> toLocalDtoList(List<MultipartFile>fileList,List<ImageSortDto> imageSortDto,String uploadDir){
        List<ImageInfoDto> result = new ArrayList<>();
        //TODO: 예외처리 하나 있으면 좋아보인다. mapping Exception 이런것들

        for(int i = 0 ; i < fileList.size(); i++){
            ImageInfoDto imageInfoDto = toLocalDto(fileList.get(i), imageSortDto.get(i), uploadDir);
            result.add(imageInfoDto);
        }
        return result;
    }

}
