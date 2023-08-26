package project.main.webstore.domain.review.controller;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.MultiValueMap;
import project.main.webstore.annotation.WithMockCustomUser;
import project.main.webstore.domain.review.dto.ReviewPostRequestDto;
import project.main.webstore.domain.review.entity.Review;
import project.main.webstore.domain.review.service.ReviewGetService;
import project.main.webstore.domain.review.service.ReviewService;
import project.main.webstore.domain.review.stub.ReviewStub;
import project.main.webstore.domain.users.enums.UserRole;
import project.main.webstore.helper.TestUtils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;


@SpringBootTest
@AutoConfigureMockMvc
@MockBean(JpaMetamodelMappingContext.class)
class ReviewControllerTest {
    @Autowired
    MockMvc mvc;
    @MockBean
    ReviewGetService getService;
    @MockBean
    ReviewService service;

    final String DEFAULT_URL = "/api";
    ReviewStub reviewStub = new ReviewStub();
    TestUtils utils = new TestUtils();
    Long userId = 1L;
    Long itemId = 2L;
    Long reviewId = 3L;
    Long imageId = 4L;
    @Autowired
    Gson gson;

    @Test
    @DisplayName("리뷰 등록 성공")
    @WithMockCustomUser(username = "순자", role = "CLIENT", email = "client@gmail.com",userId = 1L, userRole = UserRole.CLIENT)
    void post_review_test() throws Exception{
        // given
        Review expect = reviewStub.createReview(userId, itemId, reviewId);
        ReviewPostRequestDto post = reviewStub.createReviewPostDto();
        String content = gson.toJson(post);

        given(service.postReview(any(Review.class))).willReturn(expect);

        HttpHeaders defaultHeader = utils.getDefaultHeader();
        // when
        ResultActions perform = mvc.perform(MockMvcRequestBuilders
                .post(DEFAULT_URL + "/items/{itemId}/reviews", itemId)
                .headers(defaultHeader)
                .content(content));
        // then
        perform
                .andDo(log())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.userId").value(userId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.itemId").value(itemId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.reviewId").value(reviewId));
    }

    @Test
    @DisplayName("리뷰 단건 조회")
    void get_review_test() throws Exception{
        // given
        Review review = reviewStub.createReview(userId, itemId, reviewId);

        given(getService.getReviewByReviewId(anyLong())).willReturn(review);
        HttpHeaders defaultHeader = utils.getDefaultHeader();
        // when
        ResultActions perform = mvc.perform(MockMvcRequestBuilders.get(DEFAULT_URL + "/reviews/{reviewId}", reviewId).headers(defaultHeader));
        // then
        perform
                .andDo(log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.reviewId").value(reviewId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.userId").value(userId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.comment").value(review.getComment()));
    }

    @Test
    @DisplayName("리뷰 전체 조회 : 페이징")
    void get_review_paging_test() throws Exception{
        // given
        MultiValueMap pageParam = utils.getPageParam();

        Page<Review> pageReview = reviewStub.createPageReview(0,30);
        given(getService.getReviewPage(any(Pageable.class))).willReturn(pageReview);
        // when
        ResultActions perform = mvc.perform(MockMvcRequestBuilders.get(DEFAULT_URL + "/reviews").params(pageParam));
        // then
        perform
                .andDo(log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].reviewId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[1].reviewId").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[2].reviewId").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[2].reviewId").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.pageNumber").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.pageSize").value(30));

    }

    @Test
    @DisplayName("해당 상품의 리뷰 조회")
    void get_review_by_item_test() throws Exception{
        // given
        MultiValueMap pageParam = utils.getPageParam();

        Page<Review> pageReview = reviewStub.createPageReviewItemSame(0,itemId,30);
        given(getService.getReviewPageByItemId(any(Pageable.class),anyLong())).willReturn(pageReview);
        // when
        ResultActions perform = mvc.perform(MockMvcRequestBuilders.get(DEFAULT_URL + "/items/{itemId}/reviews", itemId).params(pageParam));
        // then
        perform
                .andDo(log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].itemId").value(itemId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[1].itemId").value(itemId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[2].itemId").value(itemId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[2].itemId").value(itemId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.pageNumber").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.pageSize").value(30));
    }

    @Test
    @DisplayName("사용자 별 리뷰 조회")
    void get_review_by_user_test() throws Exception{
        // given
        MultiValueMap pageParam = utils.getPageParam();

        Page<Review> pageReview = reviewStub.createPageReviewUserSame(0,userId,30);
        given(getService.getReviewPageByUserId(any(Pageable.class),anyLong())).willReturn(pageReview);
        // when
        ResultActions perform = mvc.perform(MockMvcRequestBuilders.get(DEFAULT_URL + "/user/{userId}/reviews", userId).params(pageParam));
        // then
        perform
                .andDo(log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].userId").value(userId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[1].userId").value(userId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[2].userId").value(userId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[2].userId").value(userId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.pageNumber").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.pageSize").value(30));
    }



}