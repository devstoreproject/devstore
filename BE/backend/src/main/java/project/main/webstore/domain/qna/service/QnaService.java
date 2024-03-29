package project.main.webstore.domain.qna.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.item.service.ItemValidService;
import project.main.webstore.domain.qna.entity.Answer;
import project.main.webstore.domain.qna.entity.Question;
import project.main.webstore.domain.qna.enums.QnaStatus;
import project.main.webstore.domain.qna.repository.AnswerRepository;
import project.main.webstore.domain.qna.repository.QuestionRepository;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.domain.users.service.UserValidService;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class QnaService {
    private final QuestionRepository questionRepository;
    private final QnaValidService validService;
    private final AnswerRepository answerRepository;
    private final UserValidService userValidService;
    private final ItemValidService itemValidService;

    public Question postQuestion(Question question, Long userId, Long itemId) {
        User findUser = userValidService.validUser(userId);
        Item findItem = itemValidService.validItem(itemId);

        question.setUser(findUser);
        question.setItem(findItem);
        findItem.getQuestionList().add(question);

        return questionRepository.save(question);
    }

    public Question patchQuestion(Question request, Long userId) {
        Question find = validService.validUserSameWithQuestion(userId, request.getId());
        userValidService.validUserIdSame(userId, find);

        Optional.ofNullable(request.getQnaStatus()).ifPresent(find::setQnaStatus);
        Optional.ofNullable(request.getComment()).ifPresent(find::setComment);

        return find;
    }

    public void deleteQuestion(Long questionId, Long userId) {
        Question find = validService.validUserSameOrAdmin(userId, questionId);
        if (find != null)
            questionRepository.deleteById(questionId);

    }

    public Answer postAnswer(Answer answer, Long userId) {
        User user = userValidService.validUser(userId);
        Question find = validService.validQuestion(answer.getQuestion().getId());

        validService.validAnswerExistFromQuestion(find);

        find.setQnaStatus(QnaStatus.ANSWER_COMPLETE);
        answer.addQuestion(find);
        answer.setUser(user);

        return answer;
    }

    public void deleteAnswer(Long answerId) {
        Answer find = validService.validAnswer(answerId);
        find.getQuestion().setQnaStatus(QnaStatus.REGISTER);

        answerRepository.delete(find);
    }


}
