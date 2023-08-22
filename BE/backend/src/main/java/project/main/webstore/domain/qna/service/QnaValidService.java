package project.main.webstore.domain.qna.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.main.webstore.domain.qna.entity.Answer;
import project.main.webstore.domain.qna.entity.Question;
import project.main.webstore.domain.qna.exception.QnaExceptionCode;
import project.main.webstore.domain.qna.repository.AnswerRepository;
import project.main.webstore.domain.qna.repository.QuestionRepository;
import project.main.webstore.domain.users.enums.UserRole;
import project.main.webstore.exception.BusinessLogicException;

@Service
@Transactional
@RequiredArgsConstructor
public class QnaValidService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    protected Question validQuestion(Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new BusinessLogicException(QnaExceptionCode.QUESTION_NOT_FOUND));
    }

    protected Question validUserSameWithQuestion(Long userId) {
        Question find = validQuestion(userId);
        if(!find.getUser().getId().equals(userId) ){
            throw new BusinessLogicException(QnaExceptionCode.USER_NOT_SAME);
        }
        return find;
    }
    protected Question validUserSameOrAdmin(Long userId) {
        Question find = validQuestion(userId);
        if(!checkUserSameOrAdmin(userId, find)){
            throw new BusinessLogicException(QnaExceptionCode.USER_NOT_SAME);
        }
        return find;
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
