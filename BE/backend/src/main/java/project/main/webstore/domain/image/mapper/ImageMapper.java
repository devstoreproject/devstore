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
                .id(sortDto.getImageId())
                .uploadDir(uploadDir)
                .build();
    }
    public ImageInfoDto toLocalDto(ImageSortDto sortDto) {
        return new ImageInfoDto(sortDto.getImageId(), sortDto.getOrderNumber(), sortDto.isRepresentative());
    }
    public List<ImageInfoDto> toLocalDtoList(List<MultipartFile>fileList,List<ImageSortDto> imageSortDto,String uploadDir){
        List<ImageInfoDto> result = new ArrayList<>();
        //TODO: 예외처리 하나 있으면 좋아보인다. mapping Exception 이런것들
        int j = 0;
        for(int i = 0 ; i < imageSortDto.size(); i++){
            ImageInfoDto infoDto;
            if(imageSortDto.get(i).getImageId() == null){
                infoDto = toLocalDto(fileList.get(j++), imageSortDto.get(i), uploadDir);

            }else{
                infoDto = toLocalDto(imageSortDto.get(i));
            }

            result.add(infoDto);
        }
        return result;
    }

}
