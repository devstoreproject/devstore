package project.main.webstore.domain.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
public class OptionController {
    private final OptionMapper optionMapper;
    private final OptionService optionService;

    @PostMapping("/{itemId}/options")
    public ResponseEntity postOption(@PathVariable Long itemId, @RequestBody OptionPostRequestDto post, @AuthenticationPrincipal Object principal){
        CheckLoginUser.validAdmin(principal);
        ItemOption request = optionMapper.toEntity(post);
        ItemOption result = optionService.writeOption(request, itemId);
        OptionIdResponseDto response = optionMapper.toOptionIdResponse(result);
        var responseDto = ResponseDto.builder().data(response).customCode(ResponseCode.CREATED).build();
        URI uri = UriCreator.createUri("/item/", "/options/",itemId, response.getOptionId());

        return ResponseEntity.created(uri).body(responseDto);
    }

    @PatchMapping("/options/{option-Id}")
    public ResponseEntity editItemOption(@PathVariable("option-Id") @Positive Long OptionId,
                                         @RequestBody OptionPatchDto patch) {
        ItemOption request = optionMapper.toEntity(patch);
        request.setOptionId(OptionId);
        ItemOption result = optionService.editOption(request);

        ItemOption response = optionMapper.optionResponseToOption(result);
        var responseDto = ResponseDto.builder().data(response).customCode(ResponseCode.OK).build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/options/{option-Id}")
    public ResponseEntity deleteItemOption(@PathVariable("option-Id") @Positive Long optionId) {
        optionService.deleteOption(optionId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 단일
    @GetMapping("/{item-id}/options/{option-Id}")
    public ResponseEntity getItemOption(@PathVariable("item-id") @Positive Long itemId,
                                        @PathVariable("option-id") @Positive Long optionId) {
        ItemOption result = optionService.getOption(optionId);
        OptionResponseDto response = optionMapper.optionToGetResponse(result);
        var responseDto = ResponseDto.builder().data(response).customCode(ResponseCode.OK).build();

        return ResponseEntity.ok(responseDto);
    }


    // 리스트
    @GetMapping("/{item-id}/options")
    public ResponseEntity getOptionList(@PathVariable("item-id") Long itemId) {
        List<ItemOption> result = optionService.getOptions(itemId);
        List<OptionResponseDto> response = optionMapper.optionToGetListResponse(result);

        var responseDto = ResponseDto.builder().data(response).customCode(ResponseCode.OK).build();
        return ResponseEntity.ok(responseDto);
    }
}
