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

import java.util.Optional;

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
        User user = userValidService.validUser(userId);
        notice.setUser(user);
        return repository.save(notice);
    }

    //사진 있을 때
    public Notice postNotice(Notice notice, ImageInfoDto imageInfo, Long userId){
        User user = userValidService.validUser(userId);

        notice.setUser(user);

        Image image = fileUploader.uploadImage(imageInfo);
        NoticeImage noticeImage = new NoticeImage(image, notice);

        notice.addReviewImage(noticeImage);

        return repository.save(notice);
    }

    public Notice patchNotice(ImageInfoDto imageInfo, Notice notice){
        Notice findNotice = noticeGetService.getNotice(notice.getId());

        Optional.ofNullable(notice.getContent()).ifPresent(findNotice::setContent);
        Optional.ofNullable(notice.getTitle()).ifPresent(findNotice::setTitle);
        Optional.ofNullable(notice.getNoticeCategory()).ifPresent(findNotice::setNoticeCategory);

        if(imageInfo != null){
            Image image = imageUtils.patchImage(imageInfo, notice.getNoticeImage());
            NoticeImage noticeImage = new NoticeImage(image, findNotice);
            findNotice.addReviewImage(noticeImage);
        }

        return findNotice;
    }

    public void deleteNotice(Long noticeId){
        Notice notice = noticeGetService.getNotice(noticeId);

        if (notice.getNoticeImage() != null) {
            imageUtils.deleteImage(notice.getNoticeImage());
        }

        repository.delete(notice);
    }
}
