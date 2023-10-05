package project.main.webstore.domain.notice.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.Explode;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import project.main.webstore.domain.image.dto.ImageInfoDto;
import project.main.webstore.domain.image.mapper.ImageMapper;
import project.main.webstore.domain.notice.dto.NoticeGetResponseDto;
import project.main.webstore.domain.notice.dto.NoticeGetSimpleResponseDto;
import project.main.webstore.domain.notice.dto.NoticeIdResponseDto;
import project.main.webstore.domain.notice.dto.NoticePatchRequestDto;
import project.main.webstore.domain.notice.dto.NoticePostRequestDto;
import project.main.webstore.domain.notice.entity.Notice;
import project.main.webstore.domain.notice.mapper.NoticeMapper;
import project.main.webstore.domain.notice.service.NoticeGetService;
import project.main.webstore.domain.notice.service.NoticeService;
import project.main.webstore.dto.CustomPage;
import project.main.webstore.dto.ResponseDto;
import project.main.webstore.enums.ResponseCode;
import project.main.webstore.utils.UriCreator;

@Slf4j
@RestController
@RequestMapping("/api/notices")
@RequiredArgsConstructor
public class NoticeController {
    private final String UPLOAD_DIR = "notice";
    private final NoticeService service;
    private final NoticeGetService getService;
    private final NoticeMapper noticeMapper;
    private final ImageMapper imageMapper;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "201", description = "공지 등록 성공")
    public ResponseEntity<ResponseDto<NoticeIdResponseDto>> postNotice(@RequestPart NoticePostRequestDto post,
                                                                       @Parameter(description = "Image files", content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                                                                               array = @ArraySchema(schema = @Schema(type = "string", format = "binary"))), style = ParameterStyle.FORM, explode = Explode.TRUE)
                                                                       @RequestPart(required = false) MultipartFile image) {
        Notice requsetNotice = noticeMapper.toEntity(post);
        Notice responseNotice;
        if (image != null) {
            ImageInfoDto info = imageMapper.toLocalDto(image, UPLOAD_DIR);
            responseNotice = service.postNotice(requsetNotice, info, post.getUserId());
        } else {
            responseNotice = service.postNotice(requsetNotice, post.getUserId());
        }
        NoticeIdResponseDto response = noticeMapper.toResponseDto(responseNotice);
        URI uri = UriCreator.createUri(UPLOAD_DIR, responseNotice.getId());
        var responseDto = ResponseDto.<NoticeIdResponseDto>builder().data(response).customCode(ResponseCode.CREATED).build();


        return ResponseEntity.created(uri).body(responseDto);
    }

    @PatchMapping(path = "/{noticeId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200", description = "공지 수정")
    public ResponseEntity<ResponseDto<NoticeIdResponseDto>> patchNotice(@PathVariable Long noticeId,
                                                                        @RequestPart(required = false) MultipartFile image,
                                                                        @RequestPart(required = false) NoticePatchRequestDto patch) {
        Notice notice = noticeMapper.toEntity(patch, noticeId);
        ImageInfoDto info ;
        if(image != null){
            info = imageMapper.toLocalDto(image, UPLOAD_DIR);
        }

        Notice responseNotice = service.patchNotice(null, notice);

        NoticeIdResponseDto response = noticeMapper.toResponseDto(responseNotice);
        URI uri = UriCreator.createUri(UPLOAD_DIR, responseNotice.getId());
        var responseDto = ResponseDto.<NoticeIdResponseDto>builder().data(response).customCode(ResponseCode.OK).build();

        return ResponseEntity.ok().header("Location", uri.toString()).body(responseDto);
    }

    @DeleteMapping("/{noticeId}")
    @ApiResponse(responseCode = "204", description = "공지 삭제 완료")
    public ResponseEntity deleteNotice(@PathVariable Long noticeId) {
        service.deleteNotice(noticeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    @ApiResponse(responseCode = "200", description = "공지 전체 조회")
    @Parameters({
            @Parameter(name = "page", example = "0", description = "첫 페이지가 0번지"),
            @Parameter(name = "size", example = "20", description = "한번에 전달될 데이터 크기, 사이즈 기본 값 존재 생략 가능"),
            @Parameter(name = "sort", example = "createdAt",description = "정렬할 기준이 되는 필드, 기본 값이 createdAt으로 설정되어있다. 생략 가능")
    })
    public ResponseEntity<ResponseDto<CustomPage<NoticeGetSimpleResponseDto>>> getNoticeAll(@Parameter(hidden = true)@PageableDefault(sort = "createdAt",direction = Sort.Direction.DESC) Pageable pageable,
                                                                                      @RequestParam(required = false) String category) {
        Page<Notice> responseEntity = getService.getSimpleNotice(pageable, category);
        CustomPage<NoticeGetSimpleResponseDto> responsePage = noticeMapper.toGetSimplePageResponse(responseEntity);
        var responseDto = ResponseDto.<CustomPage<NoticeGetSimpleResponseDto>>builder()
                .data(responsePage)
                .customCode(ResponseCode.OK)
                .build();
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{noticeId}")
    @ApiResponse(responseCode = "200", description = "공지사항 단건 조회")
    public ResponseEntity<ResponseDto<NoticeGetResponseDto>> getNotice(@PathVariable Long noticeId) {
        Notice responseEntity = getService.getNotice(noticeId);
        NoticeGetResponseDto response = noticeMapper.toGetRseponseGetDto(responseEntity);
        var responseDto = ResponseDto.<NoticeGetResponseDto>builder()
                .data(response)
                .customCode(ResponseCode.OK)
                .build();
        return ResponseEntity.ok(responseDto);
    }
}
