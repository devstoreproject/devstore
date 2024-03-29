package project.main.webstore.domain.review.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping(path = "/items/{itemId}/reviews")
    @ApiResponse(responseCode = "201", description = "리뷰 등록 성공")
    public ResponseEntity<ResponseDto<ReviewIdResponseDto>> postReview(@PathVariable Long itemId,
                                                                       @RequestBody ReviewPostRequestDto post,
                                                                       @Parameter(hidden = true) @AuthenticationPrincipal Object principal) {
        Long userId = CheckLoginUser.getContextIdx(principal);
        Review review = reviewMapper.toEntity(post,userId,itemId);
        Review result = service.postReview(review);
        ReviewIdResponseDto response = reviewMapper.toDto(result);
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

    @GetMapping("/reviews")
    @ApiResponse(responseCode = "200", description = "리뷰 전체 조회")
    @Parameters({
            @Parameter(name = "page", example = "0", description = "첫 페이지가 0번지"),
            @Parameter(name = "size", example = "20", description = "한번에 전달될 데이터 크기, 사이즈 기본 값 존재 생략 가능"),
            @Parameter(name = "sort", example = "createdAt",description = "정렬할 기준이 되는 필드, 기본 값이 createdAt으로 설정되어있다. 생략 가능")
    })
    public ResponseEntity<ResponseDto<Page<ReviewGetResponseDto>>> getReviewAllPage(@Parameter(hidden = true)@PageableDefault(sort = "id")Pageable pageable) {
        Page<Review> reviewPage = getService.getReviewPage(pageable);
        Page<ReviewGetResponseDto> responsePageDto = reviewMapper.toGetPageResponse(reviewPage);
        var response = ResponseDto.<Page<ReviewGetResponseDto>>builder()
                .customCode(ResponseCode.OK)
                .data(responsePageDto)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/items/{itemId}/reviews")
    @ApiResponse(responseCode = "200", description = "해당 아이템에 존재하는 리뷰 전체 출력")
    @Parameters({
            @Parameter(name = "page", example = "0", description = "첫 페이지가 0번지"),
            @Parameter(name = "size", example = "20", description = "한번에 전달될 데이터 크기, 사이즈 기본 값 존재 생략 가능"),
            @Parameter(name = "sort", example = "createdAt",description = "정렬할 기준이 되는 필드, 기본 값이 createdAt으로 설정되어있다. 생략 가능")
    })
    public ResponseEntity<ResponseDto<Page<ReviewGetResponseDto>>> getReviewPageByItemId(@Parameter(hidden = true)@PageableDefault(sort = "id")Pageable pageable,
                                                                                         @PathVariable Long itemId) {
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
    @Parameters({
            @Parameter(name = "page", example = "0", description = "첫 페이지가 0번지"),
            @Parameter(name = "size", example = "20", description = "한번에 전달될 데이터 크기, 사이즈 기본 값 존재 생략 가능"),
            @Parameter(name = "sort", example = "createdAt",description = "정렬할 기준이 되는 필드, 기본 값이 createdAt으로 설정되어있다. 생략 가능")
    })
    public ResponseEntity<ResponseDto<Page<ReviewGetResponseDto>>> getReviewListByUserId(@Parameter(hidden = true)@PageableDefault(sort = "id")Pageable pageable,
                                                                                         @PathVariable Long userId) {
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
                                       @Parameter(hidden = true)@AuthenticationPrincipal Object principal
    ) {
        Long userId = CheckLoginUser.getContextIdx(principal);
        service.deleteReview(reviewId,userId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path = "/items/{itemId}/reviews/{reviewId}")
    @ApiResponse(responseCode = "200", description = "리뷰 수정")
    public ResponseEntity<ResponseDto<ReviewIdResponseDto>> patchReview(@PathVariable Long itemId,
                                                                        @PathVariable Long reviewId,
                                                                        @RequestBody(required = false) ReviewUpdateRequestDto patch,
                                                                        @Parameter(hidden = true) @AuthenticationPrincipal Object principal
    ) {
        Long userId = CheckLoginUser.getContextIdx(principal);
        Review review = reviewMapper.toEntity(patch, reviewId,userId,itemId);
        Review patchReview = service.patchReview(review);

        ReviewIdResponseDto reviewIdResponseDto = reviewMapper.toDto(patchReview);
        var responseDto = ResponseDto.<ReviewIdResponseDto>builder().data(reviewIdResponseDto).customCode(ResponseCode.OK).build();

        URI uri = UriCreator.createUri("/items", "/reviews", itemId, responseDto.getData().getReviewId());
        return ResponseEntity.ok().header("Location", uri.toString()).body(responseDto);
    }

    @ApiResponse(responseCode = "200", description = "리뷰 좋아요 \n 로그인 회원만 사용 가능")
    @PostMapping("/items/{itemId}/reviews/{reviewId}/like")
    public ResponseEntity<ResponseDto<ReviewLikeResponseDto>> addLikeReview(@PathVariable Long reviewId,
                                                                            @PathVariable Long itemId,
                                                                            @Parameter(hidden = true) @AuthenticationPrincipal Object principal) {
        Long userId = CheckLoginUser.getContextIdx(principal);
        Boolean like = service.addLikeReview(reviewId, itemId, userId);
        ReviewLikeResponseDto response = reviewMapper.toDto(like);
        var responseDto = ResponseDto.<ReviewLikeResponseDto>builder().data(response).customCode(ResponseCode.OK).build();

        return ResponseEntity.ok().body(responseDto);
    }

    @ApiResponse(responseCode = "200", description = "특정 상품에서 좋아요가 가장 많은 리뷰 순으로 정렬, count 만큼 반환")
    @GetMapping("/items/{itemId}/reviews/best")
    public ResponseEntity<ResponseDto<List<ReviewGetResponseDto>>> getBestReview(@PathVariable Long itemId,
                                                                                 @RequestParam int count) {
        List<Review> result = getService.getBestReview(itemId, count);
        List<ReviewGetResponseDto> response = reviewMapper.toGetListResponse(result);

        var responseDto = ResponseDto.<List<ReviewGetResponseDto>>builder()
                .data(response)
                .customCode(ResponseCode.OK)
                .build();
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/reviews/best")
    @ApiResponse(responseCode = "200", description = "관리자가 정한 베스트 리뷰")
    public ResponseEntity<ResponseDto<List<ReviewGetResponseDto>>> getAdminPickBestReview(){
        List<Review> result = getService.getBestReviewByAdmin();
        List<ReviewGetResponseDto> response = reviewMapper.toGetListResponse(result);
        var responseDto = ResponseDto.<List<ReviewGetResponseDto>>builder()
                .data(response)
                .customCode(ResponseCode.OK)
                .build();
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/reviews/best")
    @ApiResponse(responseCode = "200", description = "관리자가 베스트 리뷰 등록")
    public ResponseEntity<ResponseDto<ReviewBestResponseDto>> pickBestReviewByAdmin(@RequestBody ReviewBestRequestDto post){
        List<Review> result = service.bestReviewByAdmin(post.getReviewIdList());
        ReviewBestResponseDto response = reviewMapper.toGetBestListResponse(result);
        var responseDto = ResponseDto.<ReviewBestResponseDto>builder()
                .data(response)
                .customCode(ResponseCode.OK)
                .build();
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/reviews/best")
    @ApiResponse(responseCode = "204", description = "관리자가 베스트 리뷰 삭제")
    public ResponseEntity deleteBestReviewByAdmin(@RequestBody ReviewBestRequestDto post){
        List<Review> result = service.deleteBestReview(post.getReviewIdList());
        return ResponseEntity.noContent().build();
    }
}