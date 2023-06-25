package project.main.webstore.domain.qna.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.main.webstore.domain.qna.entity.Answer;
import project.main.webstore.domain.qna.entity.Question;
import project.main.webstore.domain.qna.enums.QnaStatus;
import project.main.webstore.domain.qna.exception.QnaExceptionCode;
import project.main.webstore.domain.qna.repository.AnswerRepository;
import project.main.webstore.domain.qna.repository.QuestionRepository;
import project.main.webstore.exception.BusinessLogicException;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class QnaService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public Question postQuestion(Question question) {
        return questionRepository.save(question);
    }

    public Question patchQuestion(Question request){
        Question find = validQuestion(request.getId());

        Optional.ofNullable(request.getQnaStatus()).ifPresent(find::setQnaStatus);
        Optional.ofNullable(request.getComment()).ifPresent(find::setComment);
        Optional.ofNullable(request.isSecret()).ifPresent(find::setSecret);

        return find;
    }

    public void deleteQuestion(Long questionId){
        questionRepository.deleteById(questionId);
    }

    public Question postAnswer(Long userId,Answer answer){
        //userId 조회하는 기능 필요
        Question find = validQuestion(answer.getQuestion().getId());
        find.setQnaStatus(QnaStatus.ANSWER_COMPLETE);
        answer.addQuestion(find);

        return find;
    }

    public void deleteAnswer(Long userId, Long answerId){
        answerRepository.deleteById(answerId);
    }

    private Question validQuestion(Long questionId) {
        return questionRepository.findById(questionId).orElseThrow(() -> new BusinessLogicException(QnaExceptionCode.QUESTION_NOT_FOUND));
    }
}
