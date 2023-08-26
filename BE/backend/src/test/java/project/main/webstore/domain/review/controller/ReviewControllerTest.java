package project.main.webstore.domain.review.controller;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import project.main.webstore.annotation.WithMockCustomUser;
import project.main.webstore.domain.image.mapper.ImageMapper;
import project.main.webstore.domain.qna.mapper.QnaMapper;
import project.main.webstore.domain.review.dto.ReviewPostRequestDto;
import project.main.webstore.domain.review.entity.Review;
import project.main.webstore.domain.review.service.ReviewGetService;
import project.main.webstore.domain.review.service.ReviewService;
import project.main.webstore.domain.review.stub.ReviewStub;
import project.main.webstore.domain.users.enums.UserRole;
import project.main.webstore.helper.TestUtils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;


@SpringBootTest
@AutoConfigureMockMvc
@MockBean(JpaMetamodelMappingContext.class)
@Import({QnaMapper.class,ImageMapper.class})
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

}