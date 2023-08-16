package project.main.webstore.domain.qna.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.item.service.ItemService;
import project.main.webstore.domain.qna.entity.Answer;
import project.main.webstore.domain.qna.entity.Question;
import project.main.webstore.domain.qna.enums.QnaStatus;
import project.main.webstore.domain.qna.exception.QnaExceptionCode;
import project.main.webstore.domain.qna.repository.AnswerRepository;
import project.main.webstore.domain.qna.repository.QuestionRepository;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.domain.users.enums.UserRole;
import project.main.webstore.domain.users.service.UserValidService;
import project.main.webstore.exception.BusinessLogicException;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class QnaService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final UserValidService userValidService;
    private final ItemService itemService;
    public Question postQuestion(Question question,Long userId, Long itemId) {
        User findUser = userValidService.validUser(userId);
        Item findItem = itemService.validItem(itemId);
        question.setUser(findUser);
        question.setItem(findItem);
        findItem.getQuestionList().add(question);
        return questionRepository.save(question);
    }

    public Question patchQuestion(Question request,Long userId){
        Question find = validUserSame(request.getId());

        Optional.ofNullable(request.getQnaStatus()).ifPresent(find::setQnaStatus);
        Optional.ofNullable(request.getComment()).ifPresent(find::setComment);

        return find;
    }

    public void deleteQuestion(Long questionId,Long userId){
        validUserSameOrAdmin(userId);
        questionRepository.deleteById(questionId);
    }

    public Answer postAnswer(Answer answer,Long userId){
        User user = userValidService.validUser(userId);
        Question find = validQuestion(answer.getQuestion().getId());

        if(find.getAnswer() !=null){
            throw new BusinessLogicException(QnaExceptionCode.ANSWER_ALREADY_EXIST);
        }

        find.setQnaStatus(QnaStatus.ANSWER_COMPLETE);
        answer.addQuestion(find);
        answer.setUser(user);

        return answer;
    }

    public void deleteAnswer(Long answerId){
        Answer find = answerRepository.findById(answerId).orElseThrow(() -> new BusinessLogicException(QnaExceptionCode.QUESTION_NOT_FOUND));
        find.getQuestion().setQnaStatus(QnaStatus.ANSWER_COMPLETE);

        answerRepository.delete(find);
    }

    private Question validQuestion(Long questionId) {
        return questionRepository.findById(questionId).orElseThrow(() -> new BusinessLogicException(QnaExceptionCode.QUESTION_NOT_FOUND));
    }
    private Question validUserSame(Long userId) {
        Question find = validQuestion(userId);
        if(!find.getUser().getId().equals(userId) ){
            throw new BusinessLogicException(QnaExceptionCode.USER_NOT_SAME);
        }
        return find;
    }
    private Question validUserSameOrAdmin(Long userId) {
        Question find = validQuestion(userId);
        if(checkUserSameOrAdmin(userId, find)){
            throw new BusinessLogicException(QnaExceptionCode.USER_NOT_SAME);
        }
        return find;
    }

    private boolean checkUserSameOrAdmin(Long userId, Question find) {
        return !find.getUser().getId().equals(userId) || !find.getUser().getUserRole().equals(UserRole.ADMIN);
    }
}
