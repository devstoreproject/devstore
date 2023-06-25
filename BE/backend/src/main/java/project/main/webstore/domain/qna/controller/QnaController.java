package project.main.webstore.domain.qna.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.main.webstore.domain.qna.dto.*;
import project.main.webstore.domain.qna.entity.Answer;
import project.main.webstore.domain.qna.entity.Question;
import project.main.webstore.domain.qna.mapper.QnaMapper;
import project.main.webstore.domain.qna.service.QnaGetService;
import project.main.webstore.domain.qna.service.QnaService;
import project.main.webstore.dto.ResponseDto;
import project.main.webstore.enums.ResponseCode;
import project.main.webstore.utils.UriCreator;

import java.net.URI;

@Controller
@RestController("/api/qna")
@RequiredArgsConstructor
public class QnaController {
    private final QnaService service;
    private final QnaGetService getService;
    private final QnaMapper mapper;

    @GetMapping("/item/{itemId}")
    public ResponseEntity getQnaByItemId(Pageable pageable, @PathVariable Long itemId) {
        Page<Question> findQna = getService.findQnaByItemId(pageable, itemId);
        Page<QuestionDto> response = mapper.toResponsePage(findQna);
        var responseDto = ResponseDto.<Page<QuestionDto>>builder().data(response).customCode(ResponseCode.OK).build();
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity getQnaByUserId(Pageable pageable, @PathVariable Long userId) {
        Page<Question> findQna = getService.findQnaByUserId(pageable, userId);
        Page<QuestionDto> response = mapper.toResponsePage(findQna);
        var responseDto = ResponseDto.<Page<QuestionDto>>builder().data(response).customCode(ResponseCode.OK).build();
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{questionId}")
    public ResponseEntity getQna(Long userId, @PathVariable Long questionId){
        Question result = getService.findQuestion(userId, questionId);
        QuestionDto response = mapper.toResponseDto(result);
        var responseDto = ResponseDto.<QuestionDto>builder().data(response).customCode(ResponseCode.OK).build();
        return ResponseEntity.ok(responseDto);
    }

    //시큐리티 적용 이후 변경예정
    @GetMapping("/admin")
    public ResponseEntity getQnaByStatus(@RequestParam Long userId) {
        Page<Question> result = getService.findQuestionByStatus(userId);
        Page<QuestionDto> response = mapper.toResponsePage(result);
        var responseDto = ResponseDto.<Page<QuestionDto>>builder().data(response).customCode(ResponseCode.OK).build();
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/item/{itemId}")
    public ResponseEntity postQuestion(@PathVariable Long itemId, @RequestBody QuestionPostRequestDto postDto) {
        Question request = mapper.toEntity(postDto,itemId);
        Question result = service.postQuestion(request);
        QuestionDto response = mapper.toResponseDto(result);
        var responseDto = ResponseDto.<QuestionDto>builder().data(response).customCode(ResponseCode.OK).build();
        URI location = UriCreator.createUri("/qna/{questionId}", response.getQuestionId());
        return ResponseEntity.created(location).body(responseDto);
    }

    @PatchMapping("/item/{itemId}/{questionId}")
    public ResponseEntity patchQuestion(@PathVariable Long questionId,
                                        @PathVariable Long itemId,
                                        @RequestBody QuestionPatchDto patchDto) {
        Question request = mapper.toEntity(patchDto, questionId, itemId);
        Question result = service.patchQuestion(request);
        QuestionDto response = mapper.toResponseDto(result);
        var responseDto = ResponseDto.<QuestionDto>builder().data(response).customCode(ResponseCode.OK).build();
        URI location = UriCreator.createUri("/qna/{questionId}", response.getQuestionId());
        return ResponseEntity.ok().header("Location",location.toString()).body(responseDto);
    }

    @DeleteMapping("/qna/{questionId}")
    public ResponseEntity deleteQuestion(@PathVariable Long questionId, Long userId){
        service.deleteQuestion(questionId,userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/qna/{questionId}/answer")
    public ResponseEntity postAnswer(@PathVariable Long questionId,@RequestBody AnswerPostRequestDto postDto){
        Answer request = mapper.toEntity(postDto, questionId);
        Answer result = service.postAnswer(request);
        AnswerDto response = mapper.toResponseDto(result);
        var responseDto = ResponseDto.<AnswerDto>builder().data(response).customCode(ResponseCode.CREATED).build();
        URI location = UriCreator.createUri("/qna/{questionId}", response.getQuestionId());
        return ResponseEntity.created(location).body(responseDto);
    }

    @DeleteMapping("/qna/{questionId}/answer/{answerId}")
    public ResponseEntity deleteAnswer(@PathVariable Long answerId,@RequestParam Long userId){
        service.deleteAnswer(userId, answerId);
        return ResponseEntity.noContent().build();
    }
}
