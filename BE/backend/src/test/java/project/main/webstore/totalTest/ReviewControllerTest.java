package project.main.webstore.totalTest;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import project.main.webstore.annotation.WithMockCustomUser;
import project.main.webstore.domain.item.service.ItemService;
import project.main.webstore.domain.review.dto.ReviewBestRequestDto;
import project.main.webstore.domain.review.dto.ReviewIdResponseDto;
import project.main.webstore.domain.review.dto.ReviewPostRequestDto;
import project.main.webstore.domain.review.dto.ReviewUpdateRequestDto;
import project.main.webstore.domain.review.service.ReviewService;
import project.main.webstore.domain.users.enums.UserRole;
import project.main.webstore.domain.users.service.UserService;
import project.main.webstore.dto.ResponseDto;
import project.main.webstore.helper.TestUtils;

@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ReviewControllerTest {

    private static final String URL = "http://localhost:";
    @Container
    static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8");
    final String DEFAULT_URL = "/api";
    TestRestTemplate template = new TestRestTemplate();
    @Autowired
    TestUtils testUtils;
    @Autowired
    Gson gson;
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

    @Test
    @DisplayName("리뷰 등록")
    void post_review_test() throws Exception {
        // given
        ReviewPostRequestDto post = new ReviewPostRequestDto("본문입니다.", 10);
        HttpHeaders headers = testUtils.getJWTClient();
        HttpEntity<ReviewPostRequestDto> requestEntity = new HttpEntity<>(post, headers);
        // when

        String url = URL + port + DEFAULT_URL + "/items/{itemId}/reviews";
        ResponseEntity<String> response = template.postForEntity(url, requestEntity, String.class,
                1L);
        String body = response.getBody();
        // then
        Type responseType = new TypeToken<ResponseDto<ReviewIdResponseDto>>() {
        }.getType();
        ResponseDto<ReviewIdResponseDto> responseDto = gson.fromJson(body, responseType);

        Assertions.assertThat(responseDto.getData().getReviewId()).isEqualTo(5L);
        Assertions.assertThat(responseDto.getData().getItemId()).isEqualTo(1L);
        Assertions.assertThat(responseDto.getData().getUserId()).isEqualTo(1L);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @DisplayName("단건 조회")
    void get_review_test() throws Exception {
        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.get(DEFAULT_URL + "/reviews/{reviewId}",1L));
        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.userId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.itemId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.comment").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.best").isBoolean())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.createdAt").isString());
    }

    @Test
    @DisplayName("전체 리뷰 조회")
    @WithMockCustomUser()
    void get_all_review_test() throws Exception {
        // given
        // when
        String url = "http://localhost:" + port + DEFAULT_URL + "/reviews";
        MultiValueMap pageParam = testUtils.getPageParam();
        ResponseEntity<ResponseDto> response = template.getForEntity(url, ResponseDto.class,
                pageParam);
        // then
        ResponseDto body = response.getBody();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body.getCode()).isEqualTo("C200");
    }

    @Test
    @DisplayName("상품 별 리뷰 조회")
    void get_review_by_item_test() throws Exception {
        // given
        MultiValueMap pageParam = testUtils.getPageParam();
        // when

        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.get(DEFAULT_URL + "/items/{itemId}/reviews", 1L,
                        reviewId).params(pageParam));
        // then
        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.offset").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.size").value(30));
    }

    @Test
    @DisplayName("사용자 별 리뷰 조회")
    void get_review_by_user_test() throws Exception {
        // given
        MultiValueMap pageParam = testUtils.getPageParam();
        // when

        String url = DEFAULT_URL + "/user/{userId}/reviews";
        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.get(url, 1L)
                        .params(pageParam));
        // then
        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].userId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.offset").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.size").value(30));
    }

    @Test
    @Transactional
    @DisplayName("리뷰 삭제")
    @WithMockCustomUser(username = "순자", role = "CLIENT", email = "client@gmail.com", userId = 1L, userRole = UserRole.CLIENT)
    void delete_review_test() throws Exception {
        // given
        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.delete(DEFAULT_URL + "/items/{itemId}/reviews/{reviewId}",
                        1L, 2L));
        // then
        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("리뷰 수정")
    @Transactional
    @WithMockCustomUser(username = "순자", role = "CLIENT", email = "client@gmail.com", userId = 1L, userRole = UserRole.CLIENT)
    void patch_review_test() throws Exception {
        // given
        ReviewUpdateRequestDto reviewUpdateRequestDto = new ReviewUpdateRequestDto("수정!!!", 1);
        String content = gson.toJson(reviewUpdateRequestDto);
        HttpHeaders defaultHeader = testUtils.getDefaultHeader();

        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.patch(DEFAULT_URL + "/items/{itemId}/reviews/{reviewId}", 1L,
                        2L).content(content).headers(defaultHeader));
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
    void get_review_best_test() throws Exception {
        // given
        HttpHeaders defaultHeader = testUtils.getDefaultHeader();

        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.get(DEFAULT_URL + "/reviews/best").headers(defaultHeader));
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
    void post_review_best_test() throws Exception {
        // given
        HttpHeaders defaultHeader = testUtils.getDefaultHeader();
        ReviewBestRequestDto post = new ReviewBestRequestDto(List.of(2L));
        String content = gson.toJson(post);
        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.post(DEFAULT_URL + "/reviews/best").headers(defaultHeader)
                        .content(content));
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
    void delete_review_best_test() throws Exception {
        // given
        HttpHeaders defaultHeader = testUtils.getDefaultHeader();
        ReviewBestRequestDto post = new ReviewBestRequestDto(List.of(1L));
        String content = gson.toJson(post);
        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.delete(DEFAULT_URL + "/reviews/best").headers(defaultHeader)
                        .content(content));
        // then
        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
