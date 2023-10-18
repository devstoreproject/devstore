package project.main.webstore.domain.users.service;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.main.webstore.domain.order.entity.Orders;
import project.main.webstore.domain.order.stub.OrderStub;
import project.main.webstore.domain.qna.entity.Question;
import project.main.webstore.domain.qna.stub.QnaStub;
import project.main.webstore.domain.review.entity.Review;
import project.main.webstore.domain.review.stub.ReviewStub;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.domain.users.exception.UserExceptionCode;
import project.main.webstore.domain.users.repository.UserRepository;
import project.main.webstore.domain.users.stub.UserStub;

@ExtendWith(MockitoExtension.class)
class UserValidServiceTest {

    @InjectMocks
    UserValidService validService;
    @Mock
    UserRepository repository;
    UserStub userStub = new UserStub();
    QnaStub qnaStub = new QnaStub();
    ReviewStub reviewStub = new ReviewStub();
    OrderStub orderStub = new OrderStub();

    @Test
    @DisplayName("메일을 활용한 사용자 검증")
    void valid_user_by_email_test() throws Exception {
        // given
        String email = "admin@gmail.com";

        User user = userStub.createUser(1L);
        BDDMockito.given(repository.findByEmail(anyString())).willReturn(Optional.of(user));
        // when
        User result = validService.validByMail(email);
        // then
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    @DisplayName("메일을 활용한 사용자 검증 : 조회 실패")
    void valid_user_by_email_fail_test() throws Exception {
        // given
        String email = "admin@gmail.com";

        BDDMockito.given(repository.findByEmail(anyString())).willReturn(Optional.empty());
        // when
        // then
        Assertions.assertThatThrownBy(() -> validService.validByMail(email)).hasMessage(
                UserExceptionCode.USER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("식별자를 활용한 사용자 검증")
    void valid_user_by_id_test() throws Exception {
        // given

        long userId = 1L;
        User user = userStub.createUser(userId);
        BDDMockito.given(repository.findById(anyLong())).willReturn(Optional.of(user));
        // when
        User result = validService.validUser(userId);
        // then
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    @DisplayName("식별자를 활용한 사용자 검증 : 조회 실패")
    void valid_user_by_id_fail_test() throws Exception {
        // given
        long userId = 1L;

        BDDMockito.given(repository.findById(anyLong())).willReturn(Optional.empty());
        // when
        // then
        Assertions.assertThatThrownBy(() -> validService.validUser(userId)).hasMessage(
                UserExceptionCode.USER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("식별자를 활용한 사용자 전체 검증")
    void valid_user_by_id_all_test() throws Exception {
        // given

        long userId = 1L;
        User user = userStub.createUser(userId);
        BDDMockito.given(repository.findAllInfoById(anyLong())).willReturn(Optional.of(user));
        // when
        User result = validService.validUserAllInfo(userId);
        // then
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    @DisplayName("식별자를 활용한 사용자 검증 : 전체 조회 실패")
    void valid_user_by_id_all_fail_test() throws Exception {
        // given
        long userId = 1L;

        BDDMockito.given(repository.findAllInfoById(anyLong())).willReturn(Optional.empty());
        // when
        // then
        Assertions.assertThatThrownBy(() -> validService.validUserAllInfo(userId)).hasMessage(
                UserExceptionCode.USER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("UserId 동일한지 여부 식별 ")
    void valid_user_id_same_test() throws Exception {
        // given
        Question question = qnaStub.getQuestion();
        question.setUser(userStub.createUser(1L));

        validService.validUserIdSame(1L, question);
    }

    @Test
    @DisplayName("UserId 동일한지 여부 식별 :실패")
    void valid_user_id_same_fail_test() throws Exception {
        // given
        Question question = qnaStub.getQuestion();
        question.setUser(userStub.createUser(1L));

        Assertions.assertThatThrownBy(() -> validService.validUserIdSame(2L, question))
                .hasMessage(UserExceptionCode.USER_INFO_MISMATCH.getMessage());
    }

    @Test
    @DisplayName("UserId 동일한지 여부 식별 ")
    void valid_user_id_same_order_test() throws Exception {
        // given
        Orders order = orderStub.createOrder(1L);
        order.setUser(userStub.createUser(1L));

        validService.validUserIdSame(1L, order);
    }

    @Test
    @DisplayName("UserId 동일한지 여부 식별 :실패")
    void valid_user_id_same_fail_order_test() throws Exception {
        // given
        Orders order = orderStub.createOrder(1L);
        order.setUser(userStub.createUser(1L));

        Assertions.assertThatThrownBy(() -> validService.validUserIdSame(2L, order))
                .hasMessage(UserExceptionCode.USER_INFO_MISMATCH.getMessage());
    }

    @Test
    @DisplayName("UserId 동일한지 여부 식별 ")
    void valid_user_id_same_review_test() throws Exception {
        // given
        Review review = reviewStub.createReview(1L, 2L, 3L);

        validService.validUserIdSame(1L, review);
    }

    @Test
    @DisplayName("UserId 동일한지 여부 식별 :실패")
    void valid_user_id_same_fail_review_test() throws Exception {
        // given
        Review review = reviewStub.createReview(1L, 2L, 3L);


        Assertions.assertThatThrownBy(() -> validService.validUserIdSame(2L, review))
                .hasMessage(UserExceptionCode.USER_INFO_MISMATCH.getMessage());
    }


}