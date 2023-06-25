package project.main.webstore.domain.qna.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.main.webstore.domain.qna.entity.Question;
import project.main.webstore.domain.qna.exception.QnaExceptionCode;
import project.main.webstore.domain.qna.repository.QuestionRepository;
import project.main.webstore.exception.BusinessLogicException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QnaGetService {
    private final QuestionRepository questionRepository;

    //Item 게시판에 들어갈 로직
    public Page<Question> findQnaByItemId(Pageable pageable, Long itemId) {
        return questionRepository.findAllByItemId(pageable, itemId);
    }

    //사용자 상세 정보 페이지 들어갈 변수
    public Page<Question> findQnaByUserId(Pageable pageable, Long userId) {
        return questionRepository.findAllQnaByUserId(pageable, userId);
    }

    //관리자를 위한 질문 조회
    public Question findQuestion(Long userId, Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new BusinessLogicException(QnaExceptionCode.QUESTION_NOT_FOUND));
    }

    //관리자를 위한 미 답변 질문 리스트 체크 메서드
    public Page<Question> findQuestionByStatus(Long userId) {
        return questionRepository.findByStatus();
    }
}
