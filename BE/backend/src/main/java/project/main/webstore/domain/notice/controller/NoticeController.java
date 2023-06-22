package project.main.webstore.domain.notice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.main.webstore.domain.image.dto.ImageInfoDto;
import project.main.webstore.domain.image.mapper.ImageMapper;
import project.main.webstore.domain.notice.dto.*;
import project.main.webstore.domain.notice.entity.Notice;
import project.main.webstore.domain.notice.mapper.NoticeMapper;
import project.main.webstore.domain.notice.service.NoticeGetService;
import project.main.webstore.domain.notice.service.NoticeService;
import project.main.webstore.dto.ResponseDto;
import project.main.webstore.enums.ResponseCode;
import project.main.webstore.utils.UriCreator;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/notice")
@RequiredArgsConstructor
public class NoticeController {
    private final String UPLOAD_DIR = "notice";
    private final NoticeService service;
    private final NoticeGetService getService;
    private final NoticeMapper noticeMapper;
    private final ImageMapper imageMapper;

    @PostMapping
    public ResponseEntity postNotice(@RequestPart(required = false) List<MultipartFile> imageList,
                                     @RequestPart NoticePostRequestDto postDto) {
        Notice requsetNotice = noticeMapper.toEntity(postDto);
        Notice responseNotice;
        if (imageList != null) {
            List<ImageInfoDto> infoList = imageMapper.toLocalDtoList(imageList, postDto.getInfoList(), UPLOAD_DIR);
            responseNotice = service.postNotice(requsetNotice, infoList, postDto.getUserId());
        } else {
            responseNotice = service.postNotice(requsetNotice, postDto.getUserId());
        }
        NoticeIdResponseDto response = noticeMapper.toResponseDto(responseNotice);
        URI uri = UriCreator.createUri(UPLOAD_DIR, responseNotice.getId());
        var responseDto = ResponseDto.<NoticeIdResponseDto>builder().data(response).customCode(ResponseCode.CREATED).build();


        return ResponseEntity.created(uri).body(responseDto);
    }

    @PatchMapping("/{noticeId}")
    public ResponseEntity patchNotice(@PathVariable Long noticeId,
                                      @RequestPart(required = false) List<MultipartFile> imageList,
                                      @RequestPart NoticePatchRequestDto patchDto) {
        Notice notice = noticeMapper.toEntity(patchDto, noticeId);
        List<ImageInfoDto> infoList = imageMapper.toLocalDtoList(imageList, patchDto.getImageSortAndRepresentativeInfo(), UPLOAD_DIR);

        Notice responseNotice = service.patchNotice(infoList, patchDto.getDeleteImageId(), notice, patchDto.getUserId());

        NoticeIdResponseDto response = noticeMapper.toResponseDto(responseNotice);
        URI uri = UriCreator.createUri(UPLOAD_DIR, responseNotice.getId());
        var responseDto = ResponseDto.<NoticeIdResponseDto>builder().data(response).customCode(ResponseCode.OK).build();

        return ResponseEntity.ok().header("Location", uri.toString()).body(responseDto);
    }

    @DeleteMapping("/{noticeId}")
    public ResponseEntity deleteNotice(@PathVariable Long noticeId) {
        service.deleteNotice(noticeId);
        var responseDto = ResponseDto.builder().data(null).customCode(ResponseCode.OK).build();
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("")
    public ResponseEntity getNoticeAll(Pageable pageable) {
        Page<Notice> responseEntity = getService.getSimpleNotice(pageable);
        Page<NoticeGetSimpleResponseDto> responsePage = noticeMapper.toGetSimplePageResponse(responseEntity);
        var responseDto = ResponseDto.<Page<NoticeGetSimpleResponseDto>>builder()
                .data(responsePage)
                .customCode(ResponseCode.OK)
                .build();
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{noticeId}")
    public ResponseEntity getNotice(@PathVariable Long noticeId) {
        Notice responseEntity = getService.getNotice(noticeId);
        NoticeGetResponseDto response = noticeMapper.toGetRseponseGetDto(responseEntity);
        var responseDto = ResponseDto.<NoticeGetResponseDto>builder()
                .data(response)
                .customCode(ResponseCode.OK)
                .build();
        return ResponseEntity.ok(responseDto);
    }
}
