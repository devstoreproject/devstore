package project.main.webstore.domain.review.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.main.webstore.domain.image.dto.ImageInfoDto;
import project.main.webstore.domain.image.mapper.ImageMapper;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.review.dto.ReviewGetResponseDto;
import project.main.webstore.domain.review.dto.ReviewIdResponseDto;
import project.main.webstore.domain.review.dto.ReviewPostRequestDto;
import project.main.webstore.domain.review.dto.ReviewUpdateRequestDto;
import project.main.webstore.domain.review.entity.Review;
import project.main.webstore.domain.review.mapper.ReviewMapper;
import project.main.webstore.domain.review.service.ReviewGetService;
import project.main.webstore.domain.review.service.ReviewService;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.dto.ResponseDto;
import project.main.webstore.enums.ResponseCode;
import project.main.webstore.utils.UriCreator;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {
    private final String UPLOAD_DIR = "review";
    private final ReviewGetService getService;
    private final ReviewService service;
    private final ReviewMapper reviewMapper;
    private final ImageMapper imageMapper;

    @PostMapping("/item/{itemId}/review")
    public ResponseEntity postReview(@PathVariable Long itemId,
                                     @RequestPart ReviewPostRequestDto postDto,
                                     @RequestPart(required = false) List<MultipartFile> imageList) {
        Review review = reviewMapper.toEntity(postDto);

        Review savedReview;
        if (imageList == null) {
            savedReview = service.postReview(review, postDto.getUserId(), itemId);
        } else {
            List<ImageInfoDto> imageInfoDtoList = imageMapper.toLocalDtoList(imageList, postDto.getInfoList(), UPLOAD_DIR);
            savedReview = service.postReview(imageInfoDtoList, review, postDto.getUserId(), itemId);
        }
        savedReview.setItem(new Item(1L));
        savedReview.setUser(new User(1L));
        ReviewIdResponseDto response = reviewMapper.toDto(savedReview);
        var responseDto = ResponseDto.<ReviewIdResponseDto>builder().data(response).customCode(ResponseCode.CREATED).build();
        URI uri = UriCreator.createUri("/api/item", "/review", itemId, responseDto.getData().getReviewId());
        return ResponseEntity.created(uri).body(responseDto);
    }

    @GetMapping("/review/{reviewId}")
    public ResponseEntity getReview(@PathVariable Long reviewId) {
        Review findReview = getService.getReviewByReviewId(reviewId);
        ReviewGetResponseDto reviewGetResponseDto = reviewMapper.toGetDtoResponse(findReview);
        var responseDto = ResponseDto.<ReviewGetResponseDto>builder()
                .data(reviewGetResponseDto)
                .customCode(ResponseCode.OK)
                .build();
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/review")
    public ResponseEntity getReviewAllPage(Pageable pageable, @RequestParam Long userId) {
        Page<Review> reviewPage = getService.getReviewPage(userId, pageable);
        Page<ReviewGetResponseDto> responsePageDto = reviewMapper.toGetPageResponse(reviewPage);
        var response = ResponseDto.<Page<ReviewGetResponseDto>>builder()
                .customCode(ResponseCode.OK)
                .data(responsePageDto)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/item/{itemId}/review")
    public ResponseEntity getReviewPageByItemId(Pageable pageable, @PathVariable Long itemId) {
        Page<Review> reviewPage = getService.getReviewPageByItemId(pageable, itemId);
        Page<ReviewGetResponseDto> responsePageDto = reviewMapper.toGetPageResponse(reviewPage);
        var response = ResponseDto.<Page<ReviewGetResponseDto>>builder()
                .customCode(ResponseCode.OK)
                .data(responsePageDto)
                .build();

        return ResponseEntity.ok(response);

    }

    @GetMapping("/user/{userId}/review")
    public ResponseEntity getReviewListByUserId(Pageable pageable, @PathVariable Long userId) {
        Page<Review> reviewPage = getService.getReviewPageByUserId(pageable, userId);
        Page<ReviewGetResponseDto> responsePageDto = reviewMapper.toGetPageResponse(reviewPage);
        var response = ResponseDto.<Page<ReviewGetResponseDto>>builder()
                .customCode(ResponseCode.OK)
                .data(responsePageDto)
                .build();

        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/item/{itemId}/review/{reviewId}")
    public ResponseEntity deleteReview(@PathVariable Long itemId,
                                       @PathVariable Long reviewId
    ) {
        service.deleteReview(reviewId);
        return ResponseEntity.ok(ResponseDto.builder().data(null).customCode(ResponseCode.OK).build());
    }

    @PatchMapping("/item/{itemId}/review/{reviewId}")
    public ResponseEntity patchReview(@PathVariable Long itemId,
                                      @PathVariable Long reviewId,
                                      @RequestPart ReviewUpdateRequestDto patchDto,
                                      @RequestPart List<MultipartFile> imageList
    ) {
        Review review = reviewMapper.toEntity(patchDto, reviewId);
        List<ImageInfoDto> imageInfoList = imageMapper.toLocalDtoList(imageList, patchDto.getImageSortAndRepresentativeInfo(), UPLOAD_DIR);
        Review patchReview = service.patchReview(imageInfoList, patchDto.getDeleteImageId(), review, patchDto.getUserId(), itemId, reviewId);
        ReviewIdResponseDto reviewIdResponseDto = reviewMapper.toDto(patchReview);
        var responseDto = ResponseDto.<ReviewIdResponseDto>builder().data(reviewIdResponseDto).customCode(ResponseCode.OK).build();

        URI uri = UriCreator.createUri("/api/item", "/review", itemId, responseDto.getData().getReviewId());
        return ResponseEntity.ok().header("Location", uri.toString()).body(responseDto);
    }

    @PostMapping("/item/{itemId}/review/{reviewId}/like")
    public ResponseEntity addLikeReview(@PathVariable Long reviewId,
                                        @PathVariable Long itemId,
                                        @RequestParam Long userId) {
        Review review = service.addLikeReview(reviewId, itemId, userId);
        ReviewIdResponseDto reviewIdResponseDto = reviewMapper.toDto(review);
        var responseDto = ResponseDto.<ReviewIdResponseDto>builder().data(reviewIdResponseDto).customCode(ResponseCode.OK).build();

        URI uri = UriCreator.createUri("/api/item", "/review", review.getItem().getId(), review.getId());
        return ResponseEntity.ok().header("Location", uri.toString()).body(responseDto);
    }


    //테스트 필요
    @GetMapping("/item/{itemId}/review/{reviewId}/best")
    public ResponseEntity getBestReview(@PathVariable Long reviewId,
                                        @PathVariable Long itemId,
                                        @RequestParam Long userId,
                                        @RequestParam int count) {
        List<Review> bestReview = getService.getBestReview(reviewId, itemId, userId, count);
        List<ReviewGetResponseDto> reviewGetResponseDtos = reviewMapper.toGetListResponse(bestReview);

        var responseDto = ResponseDto.<List<ReviewGetResponseDto>>builder()
                .data(reviewGetResponseDtos)
                .customCode(ResponseCode.OK)
                .build();
        return ResponseEntity.ok(responseDto);
    }
}
