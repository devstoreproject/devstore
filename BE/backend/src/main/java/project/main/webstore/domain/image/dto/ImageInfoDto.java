package project.main.webstore.domain.image.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Getter
public class ImageInfoDto {
    private Long id;
    private MultipartFile multipartFile;
    @Setter
    private MultipartFile thumbFile;
    private int order;
    private boolean representative;
    @Setter
    private String hash;
    private String uploadDir;
    private String originalName;
    private String uploadName;
    private String fileName;
    private String ext;

    @Builder(builderMethodName = "dtoBuilder")
    public ImageInfoDto(MultipartFile multipartFile, Long id, int order, boolean representative, String uploadDir) {
        this.id = id;
        this.multipartFile = multipartFile;
        this.order = order;
        this.representative = representative;
        this.uploadDir = uploadDir;
        this.originalName = multipartFile.getOriginalFilename();
        this.ext = extractExt(originalName);
        this.uploadName = UUID.randomUUID().toString().concat(ext);
        this.fileName = uploadDir.concat("/").concat(uploadDir);
    }

    public void addThumbFileAndHash(String hash, MultipartFile file) {
        this.thumbFile = file;
        this.hash = hash;
    }

    private String extractExt(String originalFileName) {
        int idx = originalFileName.lastIndexOf(".");
        return originalFileName.substring(idx);
    }
}
