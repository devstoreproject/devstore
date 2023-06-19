package project.main.webstore.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.main.webstore.domain.image.dto.ImageInfoDto;
import project.main.webstore.domain.image.dto.ImageSortDto;
import project.main.webstore.domain.image.dto.ImageSortPatchInfo;
import project.main.webstore.domain.image.entity.Image;
import project.main.webstore.domain.image.entity.ReviewImage;
import project.main.webstore.domain.review.dto.ReviewIdResponseDto;
import project.main.webstore.domain.review.dto.ReviewPostRequestDto;
import project.main.webstore.domain.review.dto.ReviewUpdateRequestDto;
import project.main.webstore.domain.review.entity.Review;
import project.main.webstore.domain.review.repository.ReviewRepository;
import project.main.webstore.exception.BusinessLogicException;
import project.main.webstore.exception.CommonExceptionCode;
import project.main.webstore.utils.FileUploader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final String UPLOAD_DIR = "review";
    private final ReviewRepository reviewRepository;
    private final ReviewValidService reviewValidService;
    private final FileUploader fileUploader;

    public ReviewIdResponseDto postReview(ReviewPostRequestDto dto, Long userId, Long itemId, List<MultipartFile> fileList) {
        //TODO : User 검증 및 item 검증 필요
        Review review = new Review(dto);
        List<ImageSortDto> imageSortInfoList = dto.getInfoList();

        if (imageSortInfoList.size() != fileList.size()) {
            throw new BusinessLogicException(CommonExceptionCode.IMAGE_INFO_COUNT_MISMATCH);
        }

        List<ImageInfoDto> infoList = createImageInfoList(fileList, imageSortInfoList);

        //이미지 저장 로직
        List<Image> imageList = fileUploader.uploadImage(infoList);
        //이미지 DB 저장 로직
        imageList.stream().map(image -> new ReviewImage(image, review)).forEach(review::addReviewImage);
        Review savedReview = reviewRepository.save(review);
        return new ReviewIdResponseDto(savedReview.getId(), savedReview.getUser().getId(), savedReview.getItem().getId());
    }

    public ReviewIdResponseDto postReview(ReviewPostRequestDto dto, Long userId, Long itemId) {
        //TODO : User 검증 및 item 검증 필요
        //TODO: User, Iteme Review와 매핑이 필요하다.
        Review review = new Review(dto);
        Review savedReview = reviewRepository.save(review);

        return new ReviewIdResponseDto(savedReview.getId(), savedReview.getUser().getId(),savedReview.getItem().getId());
    }

    public ReviewIdResponseDto updateReview(ReviewUpdateRequestDto dto, List<MultipartFile> fileList, Long userId, Long itemId, Long reviewId) {
        //TODO: 연관관계 찾기
        Review review = reviewValidService.validReview(reviewId);
        //기본적인 정보 존재한다면 변경
        Optional.ofNullable(dto.getRating()).ifPresent((review::setRating));
        Optional.ofNullable(dto.getComment()).ifPresent((review::setComment));
        patchImage(dto.getDeleteImageId(), fileList, dto.getImageSortAndRepresentativeInfo(), review);

        return new ReviewIdResponseDto(reviewId, userId, itemId);
    }

    public void deleteReview(Long reviewId) {
        Review findReview = reviewValidService.validReview(reviewId);
        List<String> pathList = findReview.getReviewImageList().stream().map(ReviewImage::getImagePath).collect(Collectors.toList());
        deleteImage(pathList);
        reviewRepository.deleteById(reviewId);
    }


    // #### 내부 동작 메서드 #### //
    //사진 저장
    private void patchImage(List<Long> deleteImageId, List<MultipartFile> fileList, List<ImageSortPatchInfo> sortDtoList, Review review) {
        List<ReviewImage> imageList = review.getReviewImageList();

        changeRepresentative(sortDtoList, review, imageList);

        if (!deleteImageId.isEmpty()) {
            //사진 삭제하는 경우
            List<ReviewImage> deleteImage = findImageById(deleteImageId, review);
            List<String> deleteImagePath = deleteImage.stream().map(Image::getImagePath).collect(Collectors.toList());

            deleteImageList(imageList, deleteImageId, deleteImagePath);
            //사진 삭제 후 등록 진행
            if (!fileList.isEmpty()) {
                saveAndAddImage(fileList, sortDtoList, review);
            }
        }
        //사진 등록만 진행
        else {
            if (!fileList.isEmpty()) {
                saveAndAddImage(fileList, sortDtoList, review);
            }
        }
    }

    private void changeRepresentative(List<ImageSortPatchInfo> sortDtoList, Review review, List<ReviewImage> imageList) {
        //기존에 있는 대표 이미지 false 변경
        review.getReviewImageList().stream()
                .filter(Image::isRepresentative)
                .forEach(image->image.setRepresentative(false));

        //요청에서 받은 대표 이미지 번호 확인
        Optional<ImageSortPatchInfo> representative = sortDtoList.stream()
                .filter(ImageSortPatchInfo::isRepresentative)
                .findFirst();
        ImageSortPatchInfo info = representative.orElseThrow(() -> new BusinessLogicException(CommonExceptionCode.IMAGE_HAS_ALWAYS_REPRESENTATIVE));

        //대표 값이 기존에 저장된 파일 내에 있을 경우 변경
        if (info.getImageId() != null) {
            imageList.stream()
                    .filter(image -> image.getId() == info.getImageId())
                    .forEach(reviewImage -> reviewImage.setRepresentative(info.isRepresentative()));
        }
    }

    private List<ImageInfoDto> createImageInfoList(List<MultipartFile> fileList, List<?> list) {
        List<ImageInfoDto> infoList = new ArrayList<>();

        for (int i = 0; i < fileList.size(); i++) {
            Object item = list.get(i);
            int orderNumber;
            boolean representative;

            if (item instanceof ImageSortDto) {
                ImageSortDto imageSortDto = (ImageSortDto) item;
                orderNumber = imageSortDto.getOrderNumber();
                representative = imageSortDto.isRepresentative();
            } else if (item instanceof ImageSortPatchInfo) {
                ImageSortPatchInfo imageSortPatchInfo = (ImageSortPatchInfo) item;
                orderNumber = imageSortPatchInfo.getOrderNumber();
                representative = imageSortPatchInfo.isRepresentative();
            } else {
                throw new IllegalArgumentException("잘못된 요청입니다.");
            }

            ImageInfoDto infoDto = new ImageInfoDto(fileList.get(i), orderNumber, representative, UPLOAD_DIR);
            infoList.add(infoDto);
        }
        return infoList;
    }

    private void deleteImageList(List<ReviewImage> reviewImageList, List<Long> deleteId, List<String> deleteImagePath) {
        for (int i = 0; i < deleteId.size(); i++) {
            Long id = deleteId.get(i);
            int index;
            for (index = 0 ; index < reviewImageList.size(); index++){
                if(reviewImageList.get(index).getId() == id){
                    break;
                }
            }
            reviewImageList.remove(index);
            deleteImage(deleteImagePath);
        }
    }

    private void saveAndAddImage(List<MultipartFile> fileList, List<ImageSortPatchInfo> sortDtoList, Review review) {
        List<ImageSortPatchInfo> uploadImageSortInfo = sortDtoList.stream().filter(info -> info.getImageId() == null).collect(Collectors.toList());

        //검증
        if (uploadImageSortInfo.size() != fileList.size())
            throw new BusinessLogicException(CommonExceptionCode.IMAGE_ERROR);

        List<ImageInfoDto> infoList = createImageInfoList(fileList, uploadImageSortInfo);
        List<Image> images = fileUploader.uploadImage(infoList);
        images.stream().map(image -> new ReviewImage(image, review)).forEach(review::addReviewImage);
    }

    private List<ReviewImage> findImageById(List<Long> deleteImageId, Review review) {
        List<ReviewImage> reviewImage = review.getReviewImageList().stream().filter(image -> deleteImageId.contains(image.getId())).collect(Collectors.toList());
        return reviewImage;
    }

    private void deleteImage(List<String> deletePath) {
        for (String path : deletePath) {
            fileUploader.deleteS3Image(path);
        }
    }
}
