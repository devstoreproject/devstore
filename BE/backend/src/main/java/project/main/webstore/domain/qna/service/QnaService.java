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
        //TODO : userId를 찾는 로직 필요
        //item 찾는 로직 필요
        //item에 저장하는 로직 필요

        return questionRepository.save(question);
    }

    public Question patchQuestion(Question request){
        //TODO : userId 찾는 로직 필요
        Question find = validQuestion(request.getId());

        Optional.ofNullable(request.getQnaStatus()).ifPresent(find::setQnaStatus);
        Optional.ofNullable(request.getComment()).ifPresent(find::setComment);

        return find;
    }

    public void deleteQuestion(Long questionId,Long userId){
        questionRepository.deleteById(questionId);
    }

    public Answer postAnswer(Answer answer){
        //TODO : userId 조회하는 기능 필요 answer.getUser().getId() 활용
        Question find = validQuestion(answer.getQuestion().getId());
        find.setQnaStatus(QnaStatus.ANSWER_COMPLETE);
        answer.addQuestion(find);

        return answer;
    }

    public void deleteAnswer(Long userId, Long answerId){
        Answer find = answerRepository.findById(answerId).orElseThrow(() -> new BusinessLogicException(QnaExceptionCode.QUESTION_NOT_FOUND));
        find.getQuestion().setQnaStatus(QnaStatus.ANSWER_COMPLETE);

        answerRepository.delete(find);
    }

    private Question validQuestion(Long questionId) {
        return questionRepository.findById(questionId).orElseThrow(() -> new BusinessLogicException(QnaExceptionCode.QUESTION_NOT_FOUND));
    }
}
