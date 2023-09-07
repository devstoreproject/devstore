package project.main.webstore.domain.item.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.main.webstore.domain.item.dto.OptionIdResponseDto;
import project.main.webstore.domain.item.dto.OptionPatchDto;
import project.main.webstore.domain.item.dto.OptionPostRequestDto;
import project.main.webstore.domain.item.dto.OptionResponseDto;
import project.main.webstore.domain.item.entity.ItemOption;
import project.main.webstore.domain.item.mapper.OptionMapper;
import project.main.webstore.domain.item.service.OptionService;
import project.main.webstore.dto.ResponseDto;
import project.main.webstore.enums.ResponseCode;
import project.main.webstore.utils.CheckLoginUser;
import project.main.webstore.utils.UriCreator;

import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
@Tag(name = "상품 API")
public class OptionController {
    private final OptionMapper optionMapper;
    private final OptionService optionService;

    @PostMapping(path = "/{itemId}/options",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "201", description = "상품 옵션 등록 성공")
    public ResponseEntity<ResponseDto<OptionIdResponseDto>> postOption(@PathVariable Long itemId,
                                                                       @RequestBody OptionPostRequestDto post,
                                                                       @Parameter(hidden = true)@AuthenticationPrincipal Object principal) {
        CheckLoginUser.validAdmin(principal);
        ItemOption request = optionMapper.toEntity(post);
        ItemOption result = optionService.writeOption(request, itemId);
        OptionIdResponseDto response = optionMapper.toOptionIdResponse(result);
        var responseDto = ResponseDto.<OptionIdResponseDto>builder().data(response).customCode(ResponseCode.CREATED).build();
        URI uri = UriCreator.createUri("/item/", "/options/", itemId, response.getOptionId());

        return ResponseEntity.created(uri).body(responseDto);
    }

    @PatchMapping(path = "/options/{option-Id}")
    @ApiResponse(responseCode = "200", description = "상품 옵션 수정 성공")
    public ResponseEntity<ResponseDto<ItemOption>> editItemOption(@PathVariable("option-Id") @Positive Long OptionId,
                                                                  @RequestBody OptionPatchDto patch) {
        ItemOption request = optionMapper.toEntity(patch);
        request.setOptionId(OptionId);
        ItemOption result = optionService.editOption(request);

        ItemOption response = optionMapper.optionResponseToOption(result);
        var responseDto = ResponseDto.<ItemOption>builder().data(response).customCode(ResponseCode.OK).build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/options/{option-Id}")
    @ApiResponse(responseCode = "204", description = "상품 옵션 삭제 성공")
    public ResponseEntity deleteItemOption(@PathVariable("option-Id") @Positive Long optionId) {
        optionService.deleteOption(optionId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 단일
    @GetMapping("/{item-id}/options/{option-Id}")
    @ApiResponse(responseCode = "200", description = "상품 옵션 단건 조회 성공")
    public ResponseEntity<ResponseDto<OptionResponseDto>> getItemOption(@PathVariable("item-id") @Positive Long itemId,
                                                                        @PathVariable("option-id") @Positive Long optionId) {
        ItemOption result = optionService.getOption(optionId);
        OptionResponseDto response = optionMapper.optionToGetResponse(result);
        var responseDto = ResponseDto.<OptionResponseDto>builder().data(response).customCode(ResponseCode.OK).build();

        return ResponseEntity.ok(responseDto);
    }


    // 리스트
    @GetMapping("/{item-id}/options")
    @ApiResponse(responseCode = "200", description = "상품 옵션 리스트 조회 성공")
    public ResponseEntity<ResponseDto<List<OptionResponseDto>>> getOptionList(@PathVariable("item-id") Long itemId) {
        List<ItemOption> result = optionService.getOptions(itemId);
        List<OptionResponseDto> response = optionMapper.optionToGetListResponse(result);

        var responseDto = ResponseDto.<List<OptionResponseDto>>builder().data(response).customCode(ResponseCode.OK).build();
        return ResponseEntity.ok(responseDto);
    }
}
