package project.main.webstore.utils;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marvin.image.MarvinImage;
import org.marvinproject.image.transform.scale.Scale;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import project.main.webstore.domain.image.entity.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class FileUploader {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket-origin}")
    private String bucketOrigin;
    @Value("${cloud.aws.s3.bucket-resizing}")
    private String bucketResizing;

    public Image uploadImage(MultipartFile uploadFile, String uploadDir){
        String originalFilename = uploadFile.getOriginalFilename();
        String uploadImageName = createFileName(originalFilename);
        String ext = extractExt(originalFilename);

        String fileName = uploadDir + "/" + uploadImageName;
        ObjectMetadata metadata = createMetadata(uploadFile);

        String imagePath = saveImage(uploadFile,fileName,uploadDir,bucketOrigin);

        MultipartFile resizeImage = resizeImage(fileName, ext, uploadFile, 785);
        String resizedPath = saveImage(resizeImage,fileName,uploadDir,bucketResizing);

        return new Image(originalFilename,uploadImageName,imagePath,ext,resizedPath);
    }
    public String saveImage(MultipartFile uploadFile,String fileName, String uploadDir,String bucketName){
        ObjectMetadata metadata = createMetadata(uploadFile);

        try {
            amazonS3Client.putObject(
                    new PutObjectRequest(
                            bucketName, fileName, uploadFile.getInputStream(), metadata
                    ).withCannedAcl(CannedAccessControlList.PublicRead)
            );
        }catch (IOException e){
            log.error("#### S3 Upload IOException", e.getMessage());
            e.printStackTrace();
        }

        return amazonS3Client.getUrl(bucketName, fileName).toString();
    }

    private ObjectMetadata createMetadata(MultipartFile multipartFile){
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(multipartFile.getContentType());
        metadata.setContentLength(multipartFile.getSize());

        return metadata;
    }
    private String createFileName(String originalFileName) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFileName);

        return uuid.concat(ext);
    }

    private String extractExt(String originalFileName) {
        int idx = originalFileName.lastIndexOf(".");

        return originalFileName.substring(idx);
    }

    public void deleteS3Image(String imagePath) {
        String object_key = getImageKey(imagePath);
        String thum_key = object_key.replace("origin","resized");
        try {
            amazonS3Client.deleteObject(bucketOrigin, object_key);
            amazonS3Client.deleteObject(bucketResizing, thum_key);
        } catch (AmazonServiceException e) {
            log.error("#### S3 Delete AmazonServiceException", e.getMessage());
        }
    }

    private String getImageKey(String imagePath) {
        final String S3_BUCKET_PATH = "https://devstore-image-origin.s3.ap-northeast-2.amazonaws.com/";
        return imagePath.replace(S3_BUCKET_PATH, "");
    }

    public Image patchImage(MultipartFile uploadFile , String imagePath, String uploadDir) {
        deleteS3Image(imagePath);
        Image upload = uploadImage(uploadFile,uploadDir);
        return upload;
    }

    private MultipartFile resizeImage(String fileName, String ext, MultipartFile originalImage, int targetWidth) {
        try {
            // 리사이징 위한 BufferedImage 변환
            BufferedImage image = ImageIO.read(originalImage.getInputStream());
            //비율 유지를 위한 w,h 받아오기
            int originWidth = image.getWidth();
            int originHeight = image.getHeight();
            //애초에 작으면 리사이징 안하고 탈출
            if(originWidth < targetWidth)
                return originalImage;

            MarvinImage imageMarvin = new MarvinImage(image);

            Scale scale = new Scale();
            scale.load();
            scale.setAttribute("newWidth", targetWidth);
            scale.setAttribute("newHeight", targetWidth * originHeight / originWidth);
            scale.process(imageMarvin.clone(), imageMarvin, null, null, false);

            BufferedImage imageNoAlpha = imageMarvin.getBufferedImageNoAlpha();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(imageNoAlpha, ext, baos);
            baos.flush();

            return new MockMultipartFile(fileName, baos.toByteArray());

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 리사이즈에 실패했습니다.");
        }
    }
}
