package project.main.webstore.domain.qna.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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


@RestController
@RequestMapping("/api/qna")
@RequiredArgsConstructor
@Tag(name = "상품 QnA API")
public class QnaController {
    private final QnaService service;
    private final QnaGetService getService;
    private final QnaMapper mapper;

    @GetMapping("/items/{itemId}")
    @ApiResponse(responseCode = "200", description = "상품에해당하는 Qna 조회")
    public ResponseEntity<ResponseDto<Page<QuestionDto>>> getQnaByItemId(@PageableDefault(sort = "id") Pageable pageable, @PathVariable Long itemId) {
        Page<Question> findQna = getService.findQnaByItemId(pageable, itemId);
        Page<QuestionDto> response = mapper.toResponsePage(findQna);
        var responseDto = ResponseDto.<Page<QuestionDto>>builder().data(response).customCode(ResponseCode.OK).build();
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/users/{userId}")
    @ApiResponse(responseCode = "200", description = "사용자가 작성한 Qna 페이지")
    public ResponseEntity<ResponseDto<Page<QuestionDto>>> getQnaByUserId(@PageableDefault(sort = "id") Pageable pageable, @PathVariable Long userId) {
        Page<Question> findQna = getService.findQnaByUserId(pageable, userId);
        Page<QuestionDto> response = mapper.toResponsePage(findQna);
        var responseDto = ResponseDto.<Page<QuestionDto>>builder().data(response).customCode(ResponseCode.OK).build();
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{questionId}")
    @ApiResponse(responseCode = "200", description = "QnA 단건 조회")
    public ResponseEntity<ResponseDto<QuestionDto>> getQna(Long userId, @PathVariable Long questionId, @AuthenticationPrincipal Object principal) {
//        CheckLoginUser.validAdmin(principal);
        Question result = getService.findQuestion(questionId);
        QuestionDto response = mapper.toResponseDto(result);
        var responseDto = ResponseDto.<QuestionDto>builder().data(response).customCode(ResponseCode.OK).build();
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/admin")
    @ApiResponse(responseCode = "200", description = "관리자가 Qna 조회(질문 게시 상태인 것들)")
    public ResponseEntity<ResponseDto<Page<QuestionDto>>> getQnaByStatus(@RequestParam Long userId, @PageableDefault(sort = "id") Pageable pageable, @AuthenticationPrincipal Object principal) {
//        CheckLoginUser.validAdmin(principal);
        Page<Question> result = getService.findQuestionByStatus(pageable);
        Page<QuestionDto> response = mapper.toResponsePage(result);
        var responseDto = ResponseDto.<Page<QuestionDto>>builder().data(response).customCode(ResponseCode.OK).build();
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/items/{itemId}")
    @ApiResponse(responseCode = "201", description = "질문 등록")
    public ResponseEntity<ResponseDto<QuestionDto>> postQuestion(@PathVariable Long itemId, @RequestBody QuestionPostRequestDto postDto) {
        Question request = mapper.toEntity(postDto, itemId);
        Question result = service.postQuestion(request);
        QuestionDto response = mapper.toResponseDto(result);
        var responseDto = ResponseDto.<QuestionDto>builder().data(response).customCode(ResponseCode.OK).build();
        URI location = UriCreator.createUri("/qna/{questionId}", response.getQuestionId());
        return ResponseEntity.created(location).body(responseDto);
    }

    @PatchMapping("/items/{itemId}/{questionId}")
    @ApiResponse(responseCode = "200", description = "질문 수정")
    public ResponseEntity<ResponseDto<QuestionDto>> patchQuestion(@PathVariable Long questionId,
                                                                  @PathVariable Long itemId,
                                                                  @RequestBody QuestionPatchDto patchDto) {
        Question request = mapper.toEntity(patchDto, questionId, itemId);
        Question result = service.patchQuestion(request);
        QuestionDto response = mapper.toResponseDto(result);
        var responseDto = ResponseDto.<QuestionDto>builder().data(response).customCode(ResponseCode.OK).build();
        URI location = UriCreator.createUri("/qna/{questionId}", response.getQuestionId());
        return ResponseEntity.ok().header("Location", location.toString()).body(responseDto);
    }

    @DeleteMapping("/{questionId}")
    @ApiResponse(responseCode = "204", description = "질문 삭제")
    public ResponseEntity deleteQuestion(@PathVariable Long questionId, Long userId) {
        service.deleteQuestion(questionId, userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{questionId}/answer")
    @ApiResponse(responseCode = "201", description = "답변 등록")
    public ResponseEntity<ResponseDto<AnswerDto>> postAnswer(@PathVariable Long questionId, @RequestBody AnswerPostRequestDto postDto) {
        Answer request = mapper.toEntity(postDto, questionId);
        Answer result = service.postAnswer(request);
        AnswerDto response = mapper.toResponseDto(result);
        var responseDto = ResponseDto.<AnswerDto>builder().data(response).customCode(ResponseCode.CREATED).build();
        URI location = UriCreator.createUri("/qna/{questionId}", response.getQuestionId());
        return ResponseEntity.created(location).body(responseDto);
    }

    @DeleteMapping("/{questionId}/answer/{answerId}")
    @ApiResponse(responseCode = "204", description = "답변 삭제")
    public ResponseEntity deleteAnswer(@PathVariable Long answerId, @RequestParam Long userId) {
        service.deleteAnswer(userId, answerId);
        return ResponseEntity.noContent().build();
    }
}
