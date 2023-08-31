package project.main.webstore.domain.item.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.Explode;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.main.webstore.domain.image.dto.ImageInfoDto;
import project.main.webstore.domain.image.mapper.ImageMapper;
import project.main.webstore.domain.item.dto.*;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.item.enums.Category;
import project.main.webstore.domain.item.mapper.ItemMapper;
import project.main.webstore.domain.item.service.ItemService;
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
@Validated
@Tag(name = "상품 API")
public class ItemController {
    private static final String ITEM_DEFAULT_URL = "items";
    private final String UPLOAD_DIR = "item";
    private final ItemService itemService;
    private final ItemMapper itemMapper;
    private final ImageMapper imageMapper;

    //상품과 스펙, 옵션을 모두 등록
    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "201", description = "상품 등록 성공")
    public ResponseEntity<ResponseDto<ItemIdResponseDto>> createItem(@RequestPart ItemPostDto post,
                                                                     @Parameter(description = "Image files", content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                                                                             array = @ArraySchema(schema = @Schema(type = "string", format = "binary"))),style = ParameterStyle.FORM,explode = Explode.TRUE)
                                                                     @RequestPart(required = false) List<MultipartFile> imageList,
                                                                     @Parameter(hidden = true)@AuthenticationPrincipal Object principal) {
        CheckLoginUser.validAdmin(principal);

        Item request = itemMapper.toEntity(post);
        Item result;
        if (imageList != null) {
            List<ImageInfoDto> imageInfoList = imageMapper.toLocalDtoList(imageList, post.getInfoList(), UPLOAD_DIR);
            result = itemService.postItem(request, imageInfoList);
        } else {
            result = itemService.postItem(request);
        }
        ItemIdResponseDto response = itemMapper.toIdResponse(result);
        URI location = UriCreator.createUri(ITEM_DEFAULT_URL, result.getItemId());

        var responseDto = ResponseDto.<ItemIdResponseDto>builder().data(response).customCode(ResponseCode.CREATED).build();

        return ResponseEntity.created(location).body(responseDto);
    }

    //상품이든 뭐든 다 변경하는 것 (있는 것만 체크)
    @PatchMapping(
            path = "/{itemId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponse(responseCode = "200", description = "리뷰 수정 성공")
    public ResponseEntity<ResponseDto<ItemIdResponseDto>> patchItem(@PathVariable("itemId") Long itemId,
                                                                    @RequestPart(required = false) ItemPatchDto patch,
                                                                    @RequestPart(required = false) List<MultipartFile> imageList,
                                                                    @Parameter(hidden = true)@AuthenticationPrincipal Object principal) {
        CheckLoginUser.validAdmin(principal);
        Item request = itemMapper.itemPatchDtoToItem(patch);
        request.setItemId(itemId);
        List<ImageInfoDto> imageInfoDtoList = null;
        if(patch.getImageSortAndRepresentativeInfo() != null) {
            imageInfoDtoList = imageMapper.toLocalDtoList(imageList, patch.getImageSortAndRepresentativeInfo(), UPLOAD_DIR);
        }
        Item result = itemService.patchItem(imageInfoDtoList, patch.getDeleteImageId(), request);

        ItemIdResponseDto response = itemMapper.toIdResponse(result);
        URI uri = UriCreator.createUri(ITEM_DEFAULT_URL, result.getItemId());
        var responseDto = ResponseDto.<ItemIdResponseDto>builder().data(response).customCode(ResponseCode.OK).build();

        return ResponseEntity.ok().header("Location", uri.toString()).body(responseDto);
    }

    @DeleteMapping("/{item-Id}")
    @ApiResponse(responseCode = "204", description = "상품 삭제 성공")
    public ResponseEntity deleteItem(@PathVariable("item-Id") @Positive Long itemId,
                                     @Parameter(hidden = true)@AuthenticationPrincipal Object principal) {
        CheckLoginUser.validAdmin(principal);
        itemService.deleteItem(itemId);
        URI location = UriCreator.createUri(ITEM_DEFAULT_URL);
        return ResponseEntity.noContent().header("Location",location.toString()).build();
    }

    // 단일 아이템 조회
    @GetMapping("/{item-Id}")
    @ApiResponse(responseCode = "200", description = " 단건 조회")
    public ResponseEntity<ResponseDto<ItemResponseDto>> getItem(@PathVariable("item-Id") @Positive Long itemId ,
                                                                @Parameter(hidden = true)@AuthenticationPrincipal Object principal) {
        Long userId = CheckLoginUser.getContextIdx(principal);
        Item item = itemService.getItem(itemId,userId);
        ItemResponseDto response = itemMapper.toGetResponseDto(item);
        var responseDto = ResponseDto.<ItemResponseDto>builder().data(response).customCode(ResponseCode.OK).build();
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/search/itemName")
    @ApiResponse(responseCode = "200", description = "아이템 별 상품 조회 (페이징)")
    @Parameters({
            @Parameter(name = "page", example = "0", description = "첫 페이지가 0번지"),
            @Parameter(name = "size", example = "20", description = "한번에 전달될 데이터 크기, 사이즈 기본 값 존재 생략 가능"),
            @Parameter(name = "sort", example = "createdAt",description = "정렬할 기준이 되는 필드, 기본 값이 createdAt으로 설정되어있다. 생략 가능")
    })
    public ResponseEntity<ResponseDto<Page<ItemResponseDto>>> searchItem(@RequestParam String itemName,
                                                                         @Parameter(hidden = true)@PageableDefault  Pageable pageable,
                                                                         @Parameter(hidden = true)@AuthenticationPrincipal Object principal) {
        Long userId = CheckLoginUser.getContextIdx(principal);
        Page<Item> result = itemService.searchItem(itemName, pageable,userId);
        Page<ItemResponseDto> response = itemMapper.toGetPageResponse(result);
        var responseDto = ResponseDto.<Page<ItemResponseDto>>builder().data(response).customCode(ResponseCode.OK).build();

        return ResponseEntity.ok(responseDto);
    }

    // 아이템 카테고리별 조회
    @GetMapping("search/category")
    @ApiResponse(responseCode = "200", description = "상품 카테고리별 조회 페이징")
    @Parameters({
            @Parameter(name = "page", example = "0", description = "첫 페이지가 0번지"),
            @Parameter(name = "size", example = "20", description = "한번에 전달될 데이터 크기, 사이즈 기본 값 존재 생략 가능"),
            @Parameter(name = "sort", example = "createdAt",description = "정렬할 기준이 되는 필드, 기본 값이 createdAt으로 설정되어있다. 생략 가능")
    })
    public ResponseEntity<ResponseDto<Page<ItemResponseDto>>> getItemByCategory(@RequestParam Category category,
                                                                                @Parameter(hidden = true)@PageableDefault(sort = "itemId")  Pageable pageable,
                                                                                @Parameter(hidden = true)@AuthenticationPrincipal Object principal) {
        Long userId = CheckLoginUser.getContextIdx(principal);
        Page<Item> result = itemService.findItemByCategory(category, pageable,userId);
        Page<ItemResponseDto> response = itemMapper.toGetPageResponse(result);
        var responseDto = ResponseDto.<Page<ItemResponseDto>>builder().data(response).customCode(ResponseCode.OK).build();

        return ResponseEntity.ok(responseDto);
    }

    //전체 조회 (페이징)
    @GetMapping
    @ApiResponse(responseCode = "200", description = "전체 상품 조회 페이징")
    @Parameters({
            @Parameter(name = "page", example = "0", description = "첫 페이지가 0번지"),
            @Parameter(name = "size", example = "20", description = "한번에 전달될 데이터 크기, 사이즈 기본 값 존재 생략 가능"),
            @Parameter(name = "sort", example = "createdAt",description = "정렬할 기준이 되는 필드, 기본 값이 createdAt으로 설정되어있다. 생략 가능")
    })
    public ResponseEntity<ResponseDto<Page<ItemResponseDto>>> getItemByHighPrice(@Parameter(hidden = true)@PageableDefault(sort = "itemId") Pageable pageable,
                                                                                 @Parameter(hidden = true)@AuthenticationPrincipal Object principal) {
        Long userId = CheckLoginUser.getContextIdx(principal);
        Page<Item> result = itemService.findItemPage(pageable,userId);
        Page<ItemResponseDto> response = itemMapper.toGetPageResponse(result);
        var responseDto = ResponseDto.<Page<ItemResponseDto>>builder().data(response).customCode(ResponseCode.OK).build();

        return ResponseEntity.ok(responseDto);
    }

    //이미 찜이 되어있으면 최소 아니면 찜 하기 기능 구현 완료
    @PostMapping("/{itemId}/favorite")
    @ApiResponse(responseCode = "200", description = "상품 좋아요 기능")
    public ResponseEntity<ResponseDto<PickedItemDto>> pickItem(@PathVariable @Positive Long itemId,
                                                               @RequestParam Long userId,
                                                               @Parameter(hidden = true)@AuthenticationPrincipal Object principal){
        CheckLoginUser.validUserSame(principal,userId);
        PickedItemDto result = itemService.pickItem(itemId, userId);

        var responseDto = ResponseDto.<PickedItemDto>builder().data(result).customCode(ResponseCode.OK).build();
        return ResponseEntity.ok(responseDto);
    }

}
