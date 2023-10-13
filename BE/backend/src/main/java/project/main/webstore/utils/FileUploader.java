package project.main.webstore.utils;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.ByteSource;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
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
import project.main.webstore.domain.image.dto.ImageInfoDto;
import project.main.webstore.domain.image.entity.Image;
import project.main.webstore.exception.BusinessLogicException;
import project.main.webstore.exception.CommonExceptionCode;

@Slf4j
@RequiredArgsConstructor
@Component
public class FileUploader {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket-origin}")
    private String bucketOrigin;
    @Value("${cloud.aws.s3.bucket-resizing}")
    private String bucketResizing;

    //단건 업로드
    public Image uploadImage(ImageInfoDto info) {
        MultipartFile resizeImage = resizeImage(info.getMultipartFile() , info.getFileName(), info.getExt(), 300);
        String hash = createHashByMD5(resizeImage);
        //해시 값을 추가
        String imagePath = saveImage(info.getMultipartFile(), info.getFileName(), bucketOrigin);

        String thumbnailPath = saveImage(resizeImage, info.getFileName(), bucketResizing);

        return new Image(info,imagePath,thumbnailPath, hash);
    }

    public List<Image> uploadImage(List<ImageInfoDto> infoList) {
        List<Image> answer = new ArrayList<>();
        //해쉬 코드를 모두 만든다.
        List<MultipartFile> thumList = infoList.stream().map(info -> resizeImage(info.getMultipartFile(), info.getFileName(), info.getExt(), 300)).collect(Collectors.toList());
        List<String> hashList = thumList.stream().map(this::createHashByMD5).collect(Collectors.toList());


        //저장 로직 실행
        for (int i = 0; i < infoList.size(); i++) {
            ImageInfoDto info = infoList.get(i);
            String originalPath = saveImage(info.getMultipartFile(), info.getFileName(), bucketOrigin);
            String thumbnailPath = saveImage(thumList.get(i), info.getFileName(), bucketResizing);

            Image image = new Image(info, originalPath, thumbnailPath, hashList.get(i));
            answer.add(image);
        }

        return answer;
    }
    
    

    public String saveImage(MultipartFile uploadFile, String fileName, String bucketName) {
        ObjectMetadata metadata = createMetadata(uploadFile);
        try {
            amazonS3Client.putObject(
                    new PutObjectRequest(
                            bucketName, fileName, uploadFile.getInputStream(), metadata
                    ).withCannedAcl(CannedAccessControlList.PublicRead)
            );
        } catch (IOException e) {
            log.error("#### S3 Upload IOException", e.getMessage());
            e.printStackTrace();
        }

        return amazonS3Client.getUrl(bucketName, fileName).toString();
    }

    private ObjectMetadata createMetadata(MultipartFile multipartFile) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(multipartFile.getContentType());
        metadata.setContentLength(multipartFile.getSize());

        return metadata;
    }

    public void deleteS3Image(String imagePath) {
        String objectKey = getImageKey(imagePath);
        String thumbKey = objectKey.replace("origin", "resized");
        try {
            amazonS3Client.deleteObject(bucketOrigin, objectKey);
            amazonS3Client.deleteObject(bucketResizing, thumbKey);
        } catch (AmazonServiceException e) {
            log.error("#### S3 Delete AmazonServiceException", e.getMessage());
        }
    }

    private String getImageKey(String imagePath) {
        final String S3_BUCKET_PATH = "https://devstore-image-origin.s3.ap-northeast-2.amazonaws.com/";
        return imagePath.replace(S3_BUCKET_PATH, "");
    }

    private MultipartFile resizeImage(MultipartFile originalImage, String fileName, String ext, int targetWidth) {
        log.info("###target width = {}", targetWidth);
        try {
            // 리사이징 위한 BufferedImage 변환
            BufferedImage image = ImageIO.read(originalImage.getInputStream());
            //비율 유지를 위한 w,h 받아오기
            int originWidth = image.getWidth();
            int originHeight = image.getHeight();
            log.info("###original width = {}", originWidth);
            log.info("###original height = {}", originHeight);
            //애초에 작으면 리사이징 안하고 탈출
            if (originWidth < targetWidth)
                return originalImage;

            MarvinImage imageMarvin = new MarvinImage(image);

            Scale scale = new Scale();
            scale.load();
            scale.setAttribute("newWidth", targetWidth);
            scale.setAttribute("newHeight", targetWidth * originHeight / originWidth);
            scale.process(imageMarvin.clone(), imageMarvin, null, null, false);

            BufferedImage imageNoAlpha = imageMarvin.getBufferedImageNoAlpha();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(imageNoAlpha, ext.substring(1), baos);
            baos.flush();


            MultipartFile file = new MockMultipartFile(fileName, baos.toByteArray());
            log.info("### print size =  {}",file.getSize());
            log.info("#### {}" ,file.getInputStream());
            return file;
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 리사이즈에 실패했습니다.");
        }
    }

    public String createHashByMD5(MultipartFile file) {

        try {
            byte[] bytes = file.getBytes();
            ByteSource byteSource = ByteSource.wrap(bytes);
            HashCode hash = byteSource.hash(Hashing.sha256());
            return hash.toString();
        } catch (IOException e) {
            log.error("#### TransByte Error ####");
            e.printStackTrace();
            throw new BusinessLogicException(CommonExceptionCode.IMAGE_ERROR);
        }
    }
}
