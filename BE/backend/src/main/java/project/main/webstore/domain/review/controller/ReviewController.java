package project.main.webstore.domain.review.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.Explode;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.main.webstore.domain.image.dto.ImageInfoDto;
import project.main.webstore.domain.image.mapper.ImageMapper;
import project.main.webstore.domain.review.dto.*;
import project.main.webstore.domain.review.entity.Review;
import project.main.webstore.domain.review.mapper.ReviewMapper;
import project.main.webstore.domain.review.service.ReviewGetService;
import project.main.webstore.domain.review.service.ReviewService;
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

    @PostMapping(path = "/items/{itemId}/reviews",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponse(responseCode = "201", description = "리뷰 등록 성공")
    public ResponseEntity<ResponseDto<ReviewIdResponseDto>> postReview(@PathVariable Long itemId,
                                                                       @RequestPart ReviewPostRequestDto post,
                                                                       @Parameter(description = "Image file", content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                                                                               array = @ArraySchema(schema = @Schema(type = "string", format = "binary"))),style = ParameterStyle.FORM,explode = Explode.TRUE)
                                                                           @RequestPart(required = false) MultipartFile image) {
        Review review = reviewMapper.toEntity(post);

        Review savedReview;
        if (image == null) {
            savedReview = service.postReview(review, post.getUserId(), itemId);
        } else {
            ImageInfoDto imageInfo = imageMapper.toLocalDto(image, UPLOAD_DIR);
            savedReview = service.postReview(imageInfo, review, post.getUserId(), itemId);
        }
        ReviewIdResponseDto response = reviewMapper.toDto(savedReview);
        var responseDto = ResponseDto.<ReviewIdResponseDto>builder().data(response).customCode(ResponseCode.CREATED).build();
        URI uri = UriCreator.createUri("/item", "/review", itemId, responseDto.getData().getReviewId());
        return ResponseEntity.created(uri).body(responseDto);
    }

    @ApiResponse(responseCode = "200", description = "리뷰 단건 조회")
    @GetMapping("/reviews/{reviewId}")
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
    @GetMapping("/reviews")
    public ResponseEntity<ResponseDto<Page<ReviewGetResponseDto>>> getReviewAllPage(@PageableDefault(sort = "id")Pageable pageable, @RequestParam Long userId, @AuthenticationPrincipal Object principal) {
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
    @GetMapping("/items/{itemId}/reviews")
    public ResponseEntity<ResponseDto<Page<ReviewGetResponseDto>>> getReviewPageByItemId(@PageableDefault(sort = "id")Pageable pageable, @PathVariable Long itemId) {
        Page<Review> reviewPage = getService.getReviewPageByItemId(pageable, itemId);
        Page<ReviewGetResponseDto> responsePageDto = reviewMapper.toGetPageResponse(reviewPage);
        var response = ResponseDto.<Page<ReviewGetResponseDto>>builder()
                .customCode(ResponseCode.OK)
                .data(responsePageDto)
                .build();

        return ResponseEntity.ok(response);
    }

    @ApiResponse(responseCode = "200", description = "특정 회원이 작성한 리뷰 전체 조회")
    @GetMapping("/user/{userId}/reviews")
    public ResponseEntity<ResponseDto<Page<ReviewGetResponseDto>>> getReviewListByUserId(@PageableDefault(sort = "id")Pageable pageable, @PathVariable Long userId) {
        Page<Review> reviewPage = getService.getReviewPageByUserId(pageable, userId);
        Page<ReviewGetResponseDto> responsePageDto = reviewMapper.toGetPageResponse(reviewPage);
        var response = ResponseDto.<Page<ReviewGetResponseDto>>builder()
                .customCode(ResponseCode.OK)
                .data(responsePageDto)
                .build();

        return ResponseEntity.ok(response);

    }

    @ApiResponse(responseCode = "204", description = "특정 리뷰 삭제")
    @DeleteMapping("/items/{itemId}/reviews/{reviewId}")
    public ResponseEntity deleteReview(@PathVariable Long itemId,
                                       @PathVariable Long reviewId,
                                       @RequestParam Long userId,
                                       @AuthenticationPrincipal Object principal
    ) {
        CheckLoginUser.validUserSame(principal, userId);
        service.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path = "/items/{itemId}/reviews/{reviewId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200", description = "리뷰 수정")
    public ResponseEntity<ResponseDto<ReviewIdResponseDto>> patchReview(@PathVariable Long itemId,
                                                                        @PathVariable Long reviewId,
                                                                        @RequestPart(required = false) ReviewUpdateRequestDto patch,
                                                                        @RequestPart(required = false) MultipartFile image,
                                                                        @AuthenticationPrincipal Object principal
    ) {
        CheckLoginUser.validUserSame(principal, patch.getUserId());
        Review review = reviewMapper.toEntity(patch, reviewId);
        ImageInfoDto imageInfo = null;
        if (patch.getImageSortAndRepresentativeInfo()!= null) {
            imageInfo = imageMapper.toLocalDto(image, UPLOAD_DIR);
        }
        Review patchReview = service.patchReview(imageInfo, review, patch.getUserId(), itemId, reviewId);
        ReviewIdResponseDto reviewIdResponseDto = reviewMapper.toDto(patchReview);
        var responseDto = ResponseDto.<ReviewIdResponseDto>builder().data(reviewIdResponseDto).customCode(ResponseCode.OK).build();

        URI uri = UriCreator.createUri("/items", "/reviews", itemId, responseDto.getData().getReviewId());
        return ResponseEntity.ok().header("Location", uri.toString()).body(responseDto);
    }

    @ApiResponse(responseCode = "200", description = "리뷰 좋아요 \n 로그인 회원만 사용 가능")
    @PostMapping("/items/{itemId}/reviews/{reviewId}/like")
    public ResponseEntity<ResponseDto<ReviewIdResponseDto>> addLikeReview(@PathVariable Long reviewId,
                                                                          @PathVariable Long itemId,
                                                                          @RequestParam Long userId,
                                                                          @AuthenticationPrincipal Object principal) {
        CheckLoginUser.validUserSame(principal, userId);
        Review review = service.addLikeReview(reviewId, itemId, userId);
        ReviewIdResponseDto reviewIdResponseDto = reviewMapper.toDto(review);
        var responseDto = ResponseDto.<ReviewIdResponseDto>builder().data(reviewIdResponseDto).customCode(ResponseCode.OK).build();

        URI uri = UriCreator.createUri("/items", "/reviews", review.getItem().getItemId(), review.getId());
        return ResponseEntity.ok().header("Location", uri.toString()).body(responseDto);
    }

    @ApiResponse(responseCode = "200", description = "특정 상품에서 좋아요가 가장 많은 리뷰 순으로 정렬, count 만큼 반환 \n 관리자만 사용 가능")
    @GetMapping("/items/{itemId}/reviews/best")
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

    @GetMapping("/reviews/best")
    @ApiResponse(responseCode = "200", description = "관리자가 정한 베스트 리뷰, count 만큼 반환 \n 관리자만 사용 가능")
    public ResponseEntity<ResponseDto<List<ReviewGetResponseDto>>> getAdminPickBestReview(@RequestParam Long userId, @AuthenticationPrincipal Object principal){
        CheckLoginUser.validUserSame(principal,userId);

        List<Review> result = getService.getBestReviewByAdmin();
        List<ReviewGetResponseDto> response = reviewMapper.toGetListResponse(result);
        var responseDto = ResponseDto.<List<ReviewGetResponseDto>>builder()
                .data(response)
                .customCode(ResponseCode.OK)
                .build();
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/reviews/best")
    @ApiResponse(responseCode = "201", description = "관리자가 정한 베스트 리뷰, count 만큼 반환 \n 관리자만 사용 가능")
    public ResponseEntity<ResponseDto<List<ReviewGetResponseDto>>> pickBestReviewByAdmin(@RequestParam Long userId, @RequestBody ReviewBestRequestDto post, @AuthenticationPrincipal Object principal){
        CheckLoginUser.validUserSame(principal,userId);

        List<Review> result = service.bestReviewByAdmin(post.getReviewIdList());
        List<ReviewGetResponseDto> response = reviewMapper.toGetListResponse(result);
        var responseDto = ResponseDto.<List<ReviewGetResponseDto>>builder()
                .data(response)
                .customCode(ResponseCode.OK)
                .build();
        return ResponseEntity.ok(responseDto);
    }
    @DeleteMapping("/reviews/best")
    @ApiResponse(responseCode = "200", description = "관리자가 정한 베스트 리뷰, count 만큼 반환 \n 관리자만 사용 가능")
    public ResponseEntity<ResponseDto<List<ReviewGetResponseDto>>> delteBestReviewByAdmin(@RequestParam Long userId, @RequestBody ReviewBestRequestDto post, @AuthenticationPrincipal Object principal){
        CheckLoginUser.validUserSame(principal,userId);
        List<Review> result = service.deleteBestReview(post.getReviewIdList());
        List<ReviewGetResponseDto> response = reviewMapper.toGetListResponse(result);
        var responseDto = ResponseDto.<List<ReviewGetResponseDto>>builder()
                .data(response)
                .customCode(ResponseCode.OK)
                .build();
        return ResponseEntity.ok(responseDto);
    }
}
