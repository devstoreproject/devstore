package project.main.webstore.totalTest;

import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import project.main.webstore.annotation.WithMockCustomUser;
import project.main.webstore.domain.item.dto.ItemPostDto;
import project.main.webstore.domain.item.dto.OptionPostRequestDto;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.item.enums.Category;
import project.main.webstore.domain.item.service.ItemService;
import project.main.webstore.domain.review.dto.ReviewBestRequestDto;
import project.main.webstore.domain.review.dto.ReviewPostRequestDto;
import project.main.webstore.domain.review.dto.ReviewUpdateRequestDto;
import project.main.webstore.domain.review.entity.Review;
import project.main.webstore.domain.review.service.ReviewService;
import project.main.webstore.domain.users.dto.UserPostRequestDto;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.domain.users.enums.UserRole;
import project.main.webstore.domain.users.service.UserService;
import project.main.webstore.dto.ResponseDto;
import project.main.webstore.helper.TestUtils;

import java.util.LinkedHashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ReviewControllerTest {
    final String DEFAULT_URL = "/api";
    TestRestTemplate template = new TestRestTemplate();
    @Autowired
    TestUtils testUtils;
    @Autowired
    Gson gson ;
    @Autowired
    MockMvc mvc;
    Long userId;
    Long reviewId;
    Long itemId;
    @Autowired
    UserService userService;
    @Autowired
    ItemService itemService;
    @Autowired
    ReviewService reviewService;
    @LocalServerPort
    private int port;

    @BeforeEach
    void init() {
        //회원 등록
        UserPostRequestDto post = new UserPostRequestDto("client11@gmil.com", "test001!!", "김복자", "010-123-1234", "김복자");
        User user = userService.postUser(new User(post), null);
        userId = user.getId();
        //회원 상품 등록
        ItemPostDto postDto = new ItemPostDto(Category.COMPUTER, "맥북", 10, "이것은맥북입니다.", 1000000, 100, 3000, List.of(new OptionPostRequestDto("디테일1", 100, 1000, "옵션 이름")), null);
        Item item = itemService.postItem(new Item(postDto));
        itemId = item.getItemId();
        //리뷰 등록
        Review review = reviewService.postReview(new Review("본문", 10, user, item));
        reviewId = review.getId();
    }

    @AfterEach
    void deleteData() {
        reviewService.deleteReviewForTest(reviewId);
        itemService.deleteItem(itemId);
        userService.deleteUser(userId);
    }

    @Test
    @DisplayName("리뷰 등록")
    void post_review_test() throws Exception {
        // given
        ReviewPostRequestDto post = new ReviewPostRequestDto("본문입니다.", 10);
        HttpHeaders headers = testUtils.getJWTClient();
        HttpEntity<ReviewPostRequestDto> request = new HttpEntity<>(post, headers);
        // when
        ResponseEntity<ResponseDto> result = template.exchange("http://localhost:" + port + DEFAULT_URL + "/items/{itemId}/reviews",
                HttpMethod.POST,
                request,
                ResponseDto.class,
                itemId);
        // then
        ResponseDto body = result.getBody();
        LinkedHashMap data = (LinkedHashMap) body.getData();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @DisplayName("단건 조회")
    void get_review_test() throws Exception {
        // given
        ReviewPostRequestDto post = new ReviewPostRequestDto("본문입니다.", 10);
        HttpHeaders headers = testUtils.getJWTClient();
        // when
        String url = "http://localhost:" + port + DEFAULT_URL + "/reviews/{reviewId}";

        ResponseEntity<ResponseDto> response = template.getForEntity(url, ResponseDto.class, reviewId);
        // then
        ResponseDto body = response.getBody();
        LinkedHashMap data = (LinkedHashMap) body.getData();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("단건 조회")
    @WithMockCustomUser()
    void get_all_review_test() throws Exception {
        // given
        // when
        String url = "http://localhost:" + port + DEFAULT_URL + "/reviews";
        MultiValueMap pageParam = testUtils.getPageParam();
        ResponseEntity<ResponseDto> response = template.getForEntity(url,ResponseDto.class, pageParam);
        // then
        ResponseDto body = response.getBody();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body.getCode()).isEqualTo("C200");
    }

    @Test
    @DisplayName("상품 별 리뷰 조회")
    void get_review_by_item_test() throws Exception{
        // given
        MultiValueMap pageParam = testUtils.getPageParam();
        // when

        ResultActions perform = mvc.perform(MockMvcRequestBuilders.get(DEFAULT_URL + "/items/{itemId}/reviews", itemId, reviewId).params(pageParam));
        // then
        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.pageNumber").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.pageSize").value(30));
    }

    @Test
    @DisplayName("사용자 별 리뷰 조회")
    void get_review_by_user_test() throws Exception{
        // given
        MultiValueMap pageParam = testUtils.getPageParam();
        // when

        ResultActions perform = mvc.perform(MockMvcRequestBuilders.get(DEFAULT_URL + "/user/{userId}/reviews", userId).params(pageParam));
        // then
        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].userId").value(userId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.pageNumber").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.pageSize").value(30));
    }

    @Test
    @Transactional
    @DisplayName("리뷰 삭제")
    @WithMockCustomUser(username = "순자", role = "CLIENT", email = "client@gmail.com", userId = 1L, userRole = UserRole.CLIENT)
    void delete_review_test() throws Exception{
        // given
        ResultActions perform = mvc.perform(MockMvcRequestBuilders.delete(DEFAULT_URL + "/items/{itemId}/reviews/{reviewId}", itemId,2L));
        // then
        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("리뷰 수정")
    @Transactional
    @WithMockCustomUser(username = "순자", role = "CLIENT", email = "client@gmail.com", userId = 1L, userRole = UserRole.CLIENT)
    void patch_review_test() throws Exception{
        // given
        ReviewUpdateRequestDto reviewUpdateRequestDto = new ReviewUpdateRequestDto("수정!!!", 1);
        String content = gson.toJson(reviewUpdateRequestDto);
        HttpHeaders defaultHeader = testUtils.getDefaultHeader();

        ResultActions perform = mvc.perform(MockMvcRequestBuilders.patch(DEFAULT_URL + "/items/{itemId}/reviews/{reviewId}", 2L,2L).content(content).headers(defaultHeader));
        // then
        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.userId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.reviewId").value(2L));
    }

    @Test
    @DisplayName("베스트 리뷰 조회")
    @Transactional
    @WithMockCustomUser(username = "순자", role = "CLIENT", email = "client@gmail.com", userId = 1L, userRole = UserRole.CLIENT)
    void get_review_best_test() throws Exception{
        // given
        HttpHeaders defaultHeader = testUtils.getDefaultHeader();

        ResultActions perform = mvc.perform(MockMvcRequestBuilders.get(DEFAULT_URL + "/reviews/best").headers(defaultHeader));
        // then
        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].best").value(true));
    }

    @Test
    @DisplayName("베스트 리뷰 설정")
    @Transactional
    @WithMockCustomUser
    void post_review_best_test() throws Exception{
        // given
        HttpHeaders defaultHeader = testUtils.getDefaultHeader();
        ReviewBestRequestDto post = new ReviewBestRequestDto(List.of(2L));
        String content = gson.toJson(post);
        ResultActions perform = mvc.perform(MockMvcRequestBuilders.post(DEFAULT_URL + "/reviews/best").headers(defaultHeader).content(content));
        // then
        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.reviewIdList").isArray());
    }

    @Test
    @DisplayName("베스트 리뷰 제거")
    @Transactional
    @WithMockCustomUser
    void delete_review_best_test() throws Exception{
        // given
        HttpHeaders defaultHeader = testUtils.getDefaultHeader();
        ReviewBestRequestDto post = new ReviewBestRequestDto(List.of(2L));
        String content = gson.toJson(post);
        ResultActions perform = mvc.perform(MockMvcRequestBuilders.delete(DEFAULT_URL + "/reviews/best").headers(defaultHeader).content(content));
        // then
        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
