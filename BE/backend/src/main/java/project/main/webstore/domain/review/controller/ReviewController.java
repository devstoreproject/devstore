package project.main.webstore.domain.review.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import project.main.webstore.utils.CheckLoginUser;
import project.main.webstore.utils.UriCreator;

import java.net.URI;
import java.util.List;

@Tag(name = "리뷰 API", description = "리뷰 관련 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {
    private final String UPLOAD_DIR = "review";
    private final ReviewGetService getService;
    private final ReviewService service;
    private final ReviewMapper reviewMapper;
    private final ImageMapper imageMapper;

    @PostMapping(path = "/items/{itemId}/review",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponse(responseCode = "201", description = "리뷰 등록 성공")
    public ResponseEntity<ResponseDto<ReviewIdResponseDto>> postReview(@PathVariable Long itemId,
                                                                       @RequestPart ReviewPostRequestDto post,
                                                                       @RequestPart(required = false) List<MultipartFile> imageList) {
        Review review = reviewMapper.toEntity(post);

        Review savedReview;
        if (imageList == null) {
            savedReview = service.postReview(review, post.getUserId(), itemId);
        } else {
            List<ImageInfoDto> imageInfoDtoList = imageMapper.toLocalDtoList(imageList, post.getInfoList(), UPLOAD_DIR);
            savedReview = service.postReview(imageInfoDtoList, review, post.getUserId(), itemId);
        }
        savedReview.setItem(new Item(1L));
        savedReview.setUser(new User(1L));
        ReviewIdResponseDto response = reviewMapper.toDto(savedReview);
        var responseDto = ResponseDto.<ReviewIdResponseDto>builder().data(response).customCode(ResponseCode.CREATED).build();
        URI uri = UriCreator.createUri("/item", "/review", itemId, responseDto.getData().getReviewId());
        return ResponseEntity.created(uri).body(responseDto);
    }

    @ApiResponse(responseCode = "200", description = "리뷰 단건 조회")
    @GetMapping("/review/{reviewId}")
    public ResponseEntity<ResponseDto<ReviewGetResponseDto>> getReview(@PathVariable Long reviewId) {
        Review findReview = getService.getReviewByReviewId(reviewId);
        ReviewGetResponseDto reviewGetResponseDto = reviewMapper.toGetDtoResponse(findReview);
        var responseDto = ResponseDto.<ReviewGetResponseDto>builder()
                .data(reviewGetResponseDto)
                .customCode(ResponseCode.OK)
                .build();
        return ResponseEntity.ok(responseDto);
    }

    @ApiResponse(responseCode = "200", description = "리뷰 전체 조회")
    @GetMapping("/review")
    public ResponseEntity<ResponseDto<Page<ReviewGetResponseDto>>> getReviewAllPage(Pageable pageable, @RequestParam Long userId, @AuthenticationPrincipal Object principal) {
        CheckLoginUser.validUserSame(principal, userId);
        CheckLoginUser.validAdmin(principal);
        Page<Review> reviewPage = getService.getReviewPage(pageable);
        Page<ReviewGetResponseDto> responsePageDto = reviewMapper.toGetPageResponse(reviewPage);
        var response = ResponseDto.<Page<ReviewGetResponseDto>>builder()
                .customCode(ResponseCode.OK)
                .data(responsePageDto)
                .build();

        return ResponseEntity.ok(response);
    }

    @ApiResponse(responseCode = "200", description = "해당 아이템에 존재하는 리뷰 전체 출력")
    @GetMapping("/items/{itemId}/review")
    public ResponseEntity<ResponseDto<Page<ReviewGetResponseDto>>> getReviewPageByItemId(Pageable pageable, @PathVariable Long itemId) {
        Page<Review> reviewPage = getService.getReviewPageByItemId(pageable, itemId);
        Page<ReviewGetResponseDto> responsePageDto = reviewMapper.toGetPageResponse(reviewPage);
        var response = ResponseDto.<Page<ReviewGetResponseDto>>builder()
                .customCode(ResponseCode.OK)
                .data(responsePageDto)
                .build();

        return ResponseEntity.ok(response);
    }

    @ApiResponse(responseCode = "200", description = "특정 회원이 작성한 리뷰 전체 조회")
    @GetMapping("/user/{userId}/review")
    public ResponseEntity<ResponseDto<Page<ReviewGetResponseDto>>> getReviewListByUserId(Pageable pageable, @PathVariable Long userId) {
        Page<Review> reviewPage = getService.getReviewPageByUserId(pageable, userId);
        Page<ReviewGetResponseDto> responsePageDto = reviewMapper.toGetPageResponse(reviewPage);
        var response = ResponseDto.<Page<ReviewGetResponseDto>>builder()
                .customCode(ResponseCode.OK)
                .data(responsePageDto)
                .build();

        return ResponseEntity.ok(response);

    }

    @ApiResponse(responseCode = "204", description = "특정 리뷰 삭제")
    @DeleteMapping("/items/{itemId}/review/{reviewId}")
    public ResponseEntity deleteReview(@PathVariable Long itemId,
                                       @PathVariable Long reviewId,
                                       @RequestParam Long userId,
                                       @AuthenticationPrincipal Object principal
    ) {
        CheckLoginUser.validUserSame(principal, userId);
        service.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path = "/items/{itemId}/review/{reviewId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200", description = "리뷰 수정")
    public ResponseEntity<ResponseDto<ReviewIdResponseDto>> patchReview(@PathVariable Long itemId,
                                                                        @PathVariable Long reviewId,
                                                                        @RequestPart(required = false) ReviewUpdateRequestDto patch,
                                                                        @RequestPart(required = false) List<MultipartFile> imageList,
                                                                        @AuthenticationPrincipal Object principal
    ) {
        CheckLoginUser.validUserSame(principal, patch.getUserId());
        Review review = reviewMapper.toEntity(patch, reviewId);
        List<ImageInfoDto> imageInfoList = imageMapper.toLocalDtoList(imageList, patch.getImageSortAndRepresentativeInfo(), UPLOAD_DIR);
        Review patchReview = service.patchReview(imageInfoList, patch.getDeleteImageId(), review, patch.getUserId(), itemId, reviewId);
        ReviewIdResponseDto reviewIdResponseDto = reviewMapper.toDto(patchReview);
        var responseDto = ResponseDto.<ReviewIdResponseDto>builder().data(reviewIdResponseDto).customCode(ResponseCode.OK).build();

        URI uri = UriCreator.createUri("/api/item", "/review", itemId, responseDto.getData().getReviewId());
        return ResponseEntity.ok().header("Location", uri.toString()).body(responseDto);
    }

    @ApiResponse(responseCode = "200", description = "리뷰 좋아요 \n 로그인 회원만 사용 가능")
    @PostMapping("/items/{itemId}/review/{reviewId}/like")
    public ResponseEntity<ResponseDto<ReviewIdResponseDto>> addLikeReview(@PathVariable Long reviewId,
                                                                          @PathVariable Long itemId,
                                                                          @RequestParam Long userId,
                                                                          @AuthenticationPrincipal Object principal) {
        CheckLoginUser.validUserSame(principal, userId);
        Review review = service.addLikeReview(reviewId, itemId, userId);
        ReviewIdResponseDto reviewIdResponseDto = reviewMapper.toDto(review);
        var responseDto = ResponseDto.<ReviewIdResponseDto>builder().data(reviewIdResponseDto).customCode(ResponseCode.OK).build();

        URI uri = UriCreator.createUri("/api/item", "/review", review.getItem().getItemId(), review.getId());
        return ResponseEntity.ok().header("Location", uri.toString()).body(responseDto);
    }

    @ApiResponse(responseCode = "200", description = "특정 상품에서 좋아요가 가장 많은 리뷰 순으로 정렬, count 만큼 반환 \n 관리자만 사용 가능")
    @GetMapping("/items/{itemId}/review/best")
    public ResponseEntity<ResponseDto<List<ReviewGetResponseDto>>> getBestReview(@PathVariable Long itemId,
                                                                                 @RequestParam Long userId,
                                                                                 @RequestParam int count,
                                                                                 @AuthenticationPrincipal Object principal) {
        CheckLoginUser.validUserSame(principal, userId);
        CheckLoginUser.validAdmin(principal);
        List<Review> result = getService.getBestReview(itemId, count);
        List<ReviewGetResponseDto> response = reviewMapper.toGetListResponse(result);

        var responseDto = ResponseDto.<List<ReviewGetResponseDto>>builder()
                .data(response)
                .customCode(ResponseCode.OK)
                .build();
        return ResponseEntity.ok(responseDto);
    }
}
