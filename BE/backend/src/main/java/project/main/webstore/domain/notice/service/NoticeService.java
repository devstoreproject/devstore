package project.main.webstore.domain.notice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.main.webstore.domain.image.dto.ImageInfoDto;
import project.main.webstore.domain.image.entity.Image;
import project.main.webstore.domain.image.entity.NoticeImage;
import project.main.webstore.domain.image.utils.ImageUtils;
import project.main.webstore.domain.notice.entity.Notice;
import project.main.webstore.domain.notice.repository.NoticeRepository;
import project.main.webstore.utils.FileUploader;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeService {
    private NoticeRepository repository;
    private FileUploader fileUploader;
    private ImageUtils imageUtils;
    private NoticeGetService noticeGetService;
    //사진 없을 떄
    public Notice postNotice(Long userId, Notice notice){
        //TODO: user 검증 여부 체크

        //user 찾기

        return repository.save(notice);
    }

    //사진 있을 때
    public Notice postNotice(Notice notice, List<ImageInfoDto> imageInfoList, Long userId){

        //이미지 파일 검증
        imageUtils.imageValid(imageInfoList);

        //userId 조회 -> 작성 가능 여부 체크

        //이미지 저장 로직
        List<Image> imageList = fileUploader.uploadImage(imageInfoList);
        List<NoticeImage> collect = imageList.stream().map(image -> new NoticeImage(image, notice)).collect(Collectors.toList());
        notice.setNoticeImageList(collect);

        return repository.save(notice);
    }

    public Notice patchNotice(List<ImageInfoDto> imageInfoList, List<Long> deleteIdList, Notice notice,Long userId){
        Notice findNotice = noticeGetService.getNotice(notice.getId());
        Optional.ofNullable(notice.getContent()).ifPresent(findNotice::setContent);
        Optional.ofNullable(notice.getTitle()).ifPresent(findNotice::setTitle);

        patchImage(imageInfoList,notice,deleteIdList);

        return findNotice;
    }

    public void deleteNotice(Long noticeId){
        Notice notice = noticeGetService.getNotice(noticeId);

        List<NoticeImage> noticeImageList = notice.getNoticeImageList();
        List<String> deletePathList = noticeImageList.stream().map(NoticeImage::getImagePath).collect(Collectors.toList());

        imageUtils.deleteImage(deletePathList);

        repository.delete(notice);
    }

    private void patchImage(List<ImageInfoDto> infoList, Notice notice, List<Long> deleteIdList) {
        List<NoticeImage> imageList = notice.getNoticeImageList();

        if (infoList.isEmpty() == false) {
            List<ImageInfoDto> addImageList = infoList.stream().filter(info -> info.getId() == null).collect(Collectors.toList());
            List<ImageInfoDto> savedImageList = infoList.stream().filter(info -> info.getId() != null).collect(Collectors.toList());

            imageUtils.changeRepresentativeAndOrder(savedImageList, imageList);

            if (deleteIdList != null) {
                //사진 삭제하는 경우
                List<NoticeImage> deleteImage = findImageById(deleteIdList, notice);
                List<String> deleteImagePath = deleteImage.stream().map(Image::getImagePath).collect(Collectors.toList());

                imageUtils.deleteImageList(imageList, deleteIdList, deleteImagePath);
            }
            List<Image> uploadedImageList = fileUploader.uploadImage(addImageList);
            uploadedImageList.stream().map(image -> new NoticeImage(image, notice)).forEach(notice::addReviewImage);
        }
    }

    private List<NoticeImage> findImageById(List<Long> deleteImageId, Notice notice) {
        List<NoticeImage> noticeImageList = notice.getNoticeImageList().stream().filter(image -> deleteImageId.contains(image.getId())).collect(Collectors.toList());
        return noticeImageList;
    }

}
