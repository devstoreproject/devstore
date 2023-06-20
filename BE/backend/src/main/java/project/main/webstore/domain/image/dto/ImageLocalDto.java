package project.main.webstore.domain.image.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Setter
@Getter
public class ImageLocalDto {
    private Long imageId;
    private MultipartFile multipartFile;
    private MultipartFile thumbFile;
    private int order;
    private boolean representative;
    private String hash;
    private String uploadDir;
    private String originalName;
    private String uploadName;
    private String fileName;
    private String ext;
    @Builder(builderMethodName = "dtoBuilder")
    public ImageLocalDto(MultipartFile multipartFile,Long id, int order, boolean representative, String uploadDir) {
        this.imageId = id;
        this.multipartFile = multipartFile;
        this.order = order;
        this.representative = representative;
        this.uploadDir = uploadDir;
        this.originalName = multipartFile.getOriginalFilename();
        this.ext = extractExt(originalName);
        this.uploadName = UUID.randomUUID().toString().concat(ext);
        this.fileName = uploadDir.concat("/").concat(uploadDir);
    }

    private String extractExt(String originalFileName) {
        int idx = originalFileName.lastIndexOf(".");
        return originalFileName.substring(idx);
    }
}
