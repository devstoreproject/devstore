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
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.domain.users.service.UserValidService;
import project.main.webstore.utils.FileUploader;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeService {
    private final NoticeRepository repository;
    private final FileUploader fileUploader;
    private final ImageUtils imageUtils;
    private final NoticeGetService noticeGetService;
    private final UserValidService userValidService;

    //사진 없을 떄
    public Notice postNotice(Notice notice,Long userId){
        //TODO: user 검증 여부 체크
        User user = userValidService.validUser(userId);
        notice.setUser(user);
        //user 찾기
        return repository.save(notice);
    }

    //사진 있을 때
    public Notice postNotice(Notice notice, List<ImageInfoDto> imageInfoList, Long userId){
        //이미지 파일 검증
        imageUtils.imageValid(imageInfoList);
        User user = userValidService.validUser(userId);
        notice.setUser(user);
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

        if(imageInfoList != null){
            imageUtils.imageValid(imageInfoList);
            List<Image> imageList = imageUtils.patchImage(imageInfoList,findNotice.getNoticeImageList(),deleteIdList);
            if (imageList.isEmpty() == false) {
                imageList.stream().map(image -> new NoticeImage(image, notice)).forEach(findNotice::addReviewImage);
            }
        }
        return findNotice;
    }

    public void deleteNotice(Long noticeId){
        Notice notice = noticeGetService.getNotice(noticeId);

        List<NoticeImage> noticeImageList = notice.getNoticeImageList();
        List<String> deletePathList = noticeImageList.stream().map(NoticeImage::getImagePath).collect(Collectors.toList());

        imageUtils.deleteImage(deletePathList);

        repository.delete(notice);
    }


}
