package project.main.webstore.domain.item.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.main.webstore.domain.item.dto.SpecGetResponseDto;
import project.main.webstore.domain.item.dto.SpecPatchDto;
import project.main.webstore.domain.item.dto.SpecPostDto;
import project.main.webstore.domain.item.entity.ItemSpec;
import project.main.webstore.domain.item.mapper.SpecMapper;
import project.main.webstore.domain.item.service.SpecService;
import project.main.webstore.dto.ResponseDto;
import project.main.webstore.enums.ResponseCode;
import project.main.webstore.utils.CheckLoginUser;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
@Tag(name = "상품 API")
public class SpecController {
    private final SpecMapper specMapper;
    private final SpecService service;
    @PostMapping("/{item-Id}/spec")
    @ApiResponse(responseCode = "201", description = "상품 스펙 등록 성공")
    private ResponseEntity createItemSpec(@PathVariable("item-Id") @Positive Long itemId,
                                          @RequestBody @Valid SpecPostDto specPostDto) {
        ItemSpec itemSpec = specMapper.specPostDtoToSpec(specPostDto);
        ItemSpec writeSpec = service.writeSpec(itemSpec, itemId);

        return new ResponseEntity<>(specMapper.specToSpecResponse(writeSpec), HttpStatus.OK);
    }

    @PatchMapping("/{itemId}/spec/{spec-Id}")
    @ApiResponse(responseCode = "200", description = "상품 스펙 수정 성공")
    public ResponseEntity editItemSpec(@PathVariable @Positive Long itemId,
                                       @PathVariable("spec-Id") @Positive Long specId,
                                       @RequestBody SpecPatchDto specPatchDto,
                                       @AuthenticationPrincipal Object principal) {
        CheckLoginUser.validAdmin(principal);

        ItemSpec request = specMapper.specPatchDtoToSpec(specPatchDto);
        request.setSpecId(specId);
        ItemSpec updateSpec = service.editSpec(request);

        return new ResponseEntity<>(specMapper.specToSpecResponse(updateSpec), HttpStatus.OK);
    }

    @DeleteMapping("/spec/{spec-id}")
    @ApiResponse(responseCode = "204", description = "상품 스펙 삭제 성공")
    public ResponseEntity deleteItemSpec(@PathVariable("spec-id") @Positive Long specId, @AuthenticationPrincipal Object principal) {
        CheckLoginUser.validAdmin(principal);
        service.deleteSpec(specId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{itemId}/spec/{specId}")
    @ApiResponse(responseCode = "200", description = "상품 스펙 단건 조회 성공")
    public ResponseEntity getSpec(@PathVariable Long itemId,@PathVariable Long specId){
        ItemSpec spec = service.getSpec(specId);
        SpecGetResponseDto response = specMapper.toGetResponse(spec);
        var responseDto = ResponseDto.builder().customCode(ResponseCode.OK).data(response).build();
        return ResponseEntity.ok(responseDto);
    }
    @GetMapping("/{itemId}/spec")
    @ApiResponse(responseCode = "200", description = "상품 스펙 리스트 조회 성공")
    public ResponseEntity getSpecList(@PathVariable Long itemId){
        List<ItemSpec> result = service.getSpecs(itemId);
        List<SpecGetResponseDto> response = specMapper.toGetResponseList(result);
        var responseDto = ResponseDto.builder().customCode(ResponseCode.OK).data(response).build();
        return ResponseEntity.ok(responseDto);
    }


}
