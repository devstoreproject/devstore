package project.main.webstore.domain.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.main.webstore.domain.image.dto.ImageInfoDto;
import project.main.webstore.domain.image.mapper.ImageMapper;
import project.main.webstore.domain.item.dto.*;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.item.entity.ItemOption;
import project.main.webstore.domain.item.entity.ItemSpec;
import project.main.webstore.domain.item.enums.Category;
import project.main.webstore.domain.item.mapper.ItemMapper;
import project.main.webstore.domain.item.service.ItemService;
import project.main.webstore.domain.item.service.OptionService;
import project.main.webstore.domain.item.service.SpecService;
import project.main.webstore.dto.ResponseDto;
import project.main.webstore.enums.ResponseCode;
import project.main.webstore.utils.UriCreator;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/items")
@RequiredArgsConstructor
@Validated
public class ItemController {
    //    private static final String ITEM_DEFAULT_URL = "/api/items";
    private final String UPLOAD_DIR = "item";
    private final ItemService itemService;
    private final SpecService specService;
    private final OptionService optionService;
    private final ItemMapper itemMapper;
    private final ImageMapper imageMapper;

    @PostMapping("/create/item")
    public ResponseEntity createItem(@RequestPart ItemPostDto itemPostDto,
                                     @RequestPart List<MultipartFile> imageList) {
        Item writeItem = itemMapper.itemPostDtoToItem(itemPostDto);
        Item result;
        if (imageList != null) {
            List<ImageInfoDto> imageInfoList = imageMapper.toLocalDtoList(imageList, itemPostDto.getInfoList(), UPLOAD_DIR);
            result = itemService.writeItem(writeItem, imageInfoList);
        } else {
            result = itemService.writeItem(writeItem);
        }
        ItemIdResponseDto response = itemMapper.itemIdResponseDto(result);
        URI location = UriCreator.createUri(UPLOAD_DIR, result.getItemId());
        //TODO : HttpStatus 추가
        var responseDto = ResponseDto.<ItemIdResponseDto>builder().data(response).customCode(ResponseCode.CREATED).build();

        return ResponseEntity.created(location).body(responseDto);
    }

    @PatchMapping("/edit/{item-Id}")
    public ResponseEntity updateItem(@PathVariable("item-Id")
                                     @RequestPart ItemPatchDto itemPatchDto,
                                     @RequestPart List<MultipartFile> imageList) {

        Item editItem = itemMapper.itemPatchDtoToItem(itemPatchDto);
        List<ImageInfoDto> imageInfoDtoList = imageMapper.toLocalDtoList(imageList, itemPatchDto.getImageSortAndRepresentativeInfo(), UPLOAD_DIR);
        Item updateItem = itemService.editItem(imageInfoDtoList, itemPatchDto.getDeleteImageId(), editItem);
        ItemIdResponseDto response = itemMapper.itemIdResponseDto(updateItem);
        URI uri = UriCreator.createUri(UPLOAD_DIR, updateItem.getItemId());
        var responseDto = ResponseDto.builder().data(response).customCode(ResponseCode.OK).build();

        return ResponseEntity.ok().header("Location", uri.toString()).body(responseDto);
    }

    @DeleteMapping("delete/{item-Id}")
    public ResponseEntity deleteItem(@PathVariable("item-Id") @Positive Long itemId) {
        itemService.deleteItem(itemId);
        var responseDto = ResponseDto.builder().data(null).customCode(ResponseCode.OK).build();

        return ResponseEntity.ok().body(responseDto);
    }

    // 단일 아이템 조회
    @GetMapping("/{item-Id}")
    public ResponseEntity getItem(@PathVariable("item-Id") @Positive Long itemId) {
        Item item = itemService.findVerifiedItem(itemId);
        ItemResponseDto response = itemMapper.itemToItemResponseDto(item);
        var responseDto = ResponseDto.builder().data(response).customCode(ResponseCode.OK).build();

        return ResponseEntity.ok(responseDto);
    }

    // 아이템 리스트 조회
    @GetMapping("/search/itemName")
    public ResponseEntity searchItem(@RequestParam String itemName, Pageable pageable) {
        Page<Item> itemPage = itemService.searchItem(itemName, pageable);
        List<Item> items = itemPage.getContent();
        List<ItemResponseDto> itemResponseDtoList = itemMapper.itemToItemResponseDtos(items);
        var responseDto = ResponseDto.builder().data(itemResponseDtoList).customCode(ResponseCode.OK).build();

        return ResponseEntity.ok(responseDto);
    }

    // 아이템 카테고리별 조회
    @GetMapping("search/category")
    public ResponseEntity getItemByCategory(@RequestParam Category category, Pageable pageable) {
        Page<Item> itemCategoryPage = itemService.findItemByCategory(category, pageable);
        List<Item> items = itemCategoryPage.getContent();
        List<ItemResponseDto> itemResponseDtoList = itemMapper.itemToItemResponseDtos(items);
        var responseDto = ResponseDto.builder().data(itemResponseDtoList).customCode(ResponseCode.OK).build();
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("list/highPrice")
    public ResponseEntity getItemByHighPrice(Pageable pageable) {
        Page<Item> itemPricePage = itemService.findItemByHighPrice(pageable);
        List<Item> items = itemPricePage.getContent();
        List<ItemResponseDto> itemResponseDtoList = itemMapper.itemToItemResponseDtos(items);
        var responseDto = ResponseDto.builder().data(itemResponseDtoList).customCode(ResponseCode.OK).build();

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("list/lowPrice")
    public ResponseEntity getItemByLowPrice(Pageable pageable) {
        Page<Item> itemPricePage = itemService.findItemByLowPrice(pageable);
        List<Item> items = itemPricePage.getContent();
        List<ItemResponseDto> itemResponseDtoList = itemMapper.itemToItemResponseDtos(items);
        var responseDto = ResponseDto.builder().data(itemResponseDtoList).customCode(ResponseCode.OK).build();

        return ResponseEntity.ok(responseDto);
    }

    // TODO: itemSpec
    @PostMapping("/{item-Id}/spec")
    private ResponseEntity createItemSpec(@PathVariable("item-Id") @Positive Long itemId,
                                          @RequestBody @Valid SpecPostDto specPostDto) {
        ItemSpec itemSpec = itemMapper.specPostDtoToSpec(specPostDto);
        ItemSpec writeSpec = specService.writeSpec(itemSpec, itemId);

        return new ResponseEntity<>(itemMapper.specToSpecResponse(writeSpec), HttpStatus.OK);
    }

    @PatchMapping("/spec/{spec-Id}")
    public ResponseEntity editItemSpec(@PathVariable("spec-Id") @Positive Long specId,
                                       @RequestBody SpecPatchDto specPatchDto) {
        ItemSpec itemSpec = itemMapper.specPatchDtoToSpec(specPatchDto);
        itemSpec.setSpecId(specId);
        ItemSpec updateSpec = specService.editSpec(itemSpec);

        return new ResponseEntity<>(itemMapper.specToSpecResponse(updateSpec), HttpStatus.OK);
    }

    @DeleteMapping("/spec/{spec-Id}")
    public ResponseEntity deleteItemSpec(@PathVariable("spec-id") @Positive Long specId) {
        specService.deleteSpec(specId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    // 단일
//    @GetMapping("/{item-id}/spec/{spec-Id}")
//    public ResponseEntity getSpec(@PathVariable("spec-Id") Long specId,@PathVariable("item-id") Long itemId) {
//
//    }
//
//
//    // 리스트
//    @GetMapping("/spec")

    // TODO: itemOption
//    @PostMapping("/{item-Id}/spec")
//    private ResponseEntity createItemOption(@PathVariable("item-Id") @Positive Long itemId,
//                                            @RequestBody @Valid OptionPostDto optionPostDto) {
//        ItemOption itemOption = itemMapper.optionPostDtoToOption(optionPostDto);
//        ItemOption writeOption = optionService.writeOption(itemOption, itemId);
//
//        return new ResponseEntity<>(itemMapper.optionResponseToOption(writeOption), HttpStatus.OK);
//    }

    @PatchMapping("/option/{option-Id}")
    public ResponseEntity editItemOption(@PathVariable("option-Id") @Positive Long OptionId,
                                         @RequestBody OptionPatchDto OptionPatchDto) {
        ItemOption itemOption = itemMapper.optionPatchToOption(OptionPatchDto);
        itemOption.setOptionId(OptionId);
        ItemOption updateOption = optionService.editOption(itemOption);

        return new ResponseEntity<>(itemMapper.optionResponseToOption(updateOption), HttpStatus.OK);
    }

    @DeleteMapping("/option/{option-Id}")
    public ResponseEntity deleteItemOption(@PathVariable("option-Id") @Positive Long optionId) {
        optionService.deleteOption(optionId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 단일
    @GetMapping("/{item-id}/option/{option-Id}")
    public ResponseEntity getItemOption(@PathVariable("item-id") @Positive Long itemId,
                                        @PathVariable("option-id") @Positive Long optionId) {
        ItemOption result = optionService.getOption(optionId);
        OptionResponseDto response = itemMapper.optionToGetResponse(result);
        var responseDto = ResponseDto.builder().data(response).customCode(ResponseCode.OK).build();

        return ResponseEntity.ok(responseDto);
    }


    // 리스트
    @GetMapping("/{item-id}/option")
    public ResponseEntity getOptionList(@PathVariable("item-id") Long itemId) {
        List<ItemOption> result = optionService.getOptions(itemId);
        List<OptionResponseDto> response = itemMapper.optionToGetListResponse(result);

        var responseDto = ResponseDto.builder().data(response).customCode(ResponseCode.OK).build();
        return ResponseEntity.ok(responseDto);
    }


    // favorite
//    @PostMapping("/{itemId}/favorite")
//    public ResponseEntity postFavorite(@PathVariable("itemId") @Positive Long itemId) {
//        Item item = itemService.createFavorite(itemId);
//
//        return new ResponseEntity(HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{itemId}/favorite")
//    public ResponseEntity deleteFavorite(@PathVariable("itemId") @Positive Long itemId) {
//        Item item = itemService.cancelFavorite(itemId);
//
//        return new ResponseEntity(HttpStatus.OK);
//    }
}
