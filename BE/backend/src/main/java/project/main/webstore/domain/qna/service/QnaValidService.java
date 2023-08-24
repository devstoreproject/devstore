package project.main.webstore.domain.qna.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.main.webstore.domain.qna.entity.Answer;
import project.main.webstore.domain.qna.entity.Question;
import project.main.webstore.domain.qna.exception.QnaExceptionCode;
import project.main.webstore.domain.qna.repository.AnswerRepository;
import project.main.webstore.domain.qna.repository.QuestionRepository;
import project.main.webstore.domain.users.enums.UserRole;
import project.main.webstore.exception.BusinessLogicException;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class QnaValidService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    protected Question validQuestion(Long questionId) {
        Optional<Question> byId = questionRepository.findById(questionId);
        return byId
                .orElseThrow(() -> new BusinessLogicException(QnaExceptionCode.QUESTION_NOT_FOUND));
    }

    protected Question validUserSameWithQuestion(Long userId) {
        Question find = validQuestion(userId);
        if(!find.getUser().getId().equals(userId) ){
            throw new BusinessLogicException(QnaExceptionCode.USER_NOT_SAME);
        }
        return find;
    }
    protected Question validUserSameOrAdmin(Long userId,Long questionId) {
        Optional<Question> find = questionRepository.findById(userId);
        if(find.isEmpty())
            return null;
        Question findQuestion = find.get();
        if(!checkUserSameOrAdmin(userId, findQuestion)){
            throw new BusinessLogicException(QnaExceptionCode.USER_NOT_SAME);
        }
        return findQuestion;
    }

    protected Answer validAnswer(Long answerId) {
        Answer find = answerRepository.findById(answerId).orElseThrow(() -> new BusinessLogicException(QnaExceptionCode.QUESTION_NOT_FOUND));
        return find;
    }

    protected void validAnswerExistFromQuestion(Question question) {
        if (question.getAnswer() != null) {
            throw new BusinessLogicException(QnaExceptionCode.ANSWER_ALREADY_EXIST);
        }
    }

    private boolean checkUserSameOrAdmin(Long userId, Question find) {

        return find.getUser().getUserRole().equals(UserRole.ADMIN) || find.getUser().getId().equals(userId);
    }

}
