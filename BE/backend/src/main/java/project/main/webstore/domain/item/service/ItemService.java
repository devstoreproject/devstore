package project.main.webstore.domain.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.main.webstore.domain.image.dto.ImageInfoDto;
import project.main.webstore.domain.image.entity.Image;
import project.main.webstore.domain.image.entity.ItemImage;
import project.main.webstore.domain.image.utils.ImageUtils;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.item.enums.Category;
import project.main.webstore.domain.item.repository.ItemRepository;
import project.main.webstore.exception.BusinessLogicException;
import project.main.webstore.exception.CommonExceptionCode;
import project.main.webstore.utils.FileUploader;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final FileUploader fileUploader;
    private final ImageUtils imageUtils;

    // 기존 등록된 item 검증 후 등록
    public Item writeItem(Item item) {
//        Optional<Item> optionalItem = itemRepository.findByItemName(item.getItemName());
//        if(optionalItem.isPresent()) throw new BusinessLogicException(CommonExceptionCode.ITEM_EXIST);
        Item findItem = findVerifiedItem(item.getItemId());

        return itemRepository.save(findItem);
    }
    public Item writeItem(Item item, List<ImageInfoDto> imageInfoList) {
        Optional<Item> optionalItem = itemRepository.findByItemName(item.getItemName());
        if(optionalItem.isPresent()) throw new BusinessLogicException(CommonExceptionCode.ITEM_EXIST);

        //이미지를 저장하고 이미지 info를 저장한다.
        List<Image> images = fileUploader.uploadImage(imageInfoList);
        List<ItemImage> imageList = images.stream().map(image -> new ItemImage(image, item)).collect(Collectors.toList());
        item.setItemImageList(imageList);

        return itemRepository.save(item);
    }

    //TODO: findItem -?
    public Item editItem(List<ImageInfoDto> imageInfoDtoList, List<Long> deleteImageId, Item item) {
        Item findItem = findVerifiedItem(item.getItemId());

        Optional.ofNullable(item.getCategory()).ifPresent(findItem::setCategory);
        Optional.ofNullable(item.getItemName()).ifPresent(findItem::setItemName);
        Optional.ofNullable(item.getItemCount()).ifPresent(findItem::setItemCount);
        Optional.ofNullable(item.getDescription()).ifPresent(findItem::setDescription);
        Optional.ofNullable(item.getItemPrice()).ifPresent(findItem::setItemPrice);
        Optional.ofNullable(item.getDeliveryPrice()).ifPresent(findItem::setDeliveryPrice);

        // TODO:
        List<Image> imageList = imageUtils.patchImage(imageInfoDtoList, findItem.getItemImageList(), deleteImageId);

        if (imageList.isEmpty() == false) {
            imageList.stream().map(image -> new ItemImage(image, item)).forEach(findItem::addItemImage);
        }

        return itemRepository.save(findItem);
    }
    public void deleteItem(Long itemId) {
        Item findItem = findVerifiedItem(itemId);

        // TODO:
        List<ItemImage> itemImageList = findItem.getItemImageList();
        List<String> deletePatchList = itemImageList.stream().map(ItemImage::getImagePath).collect(Collectors.toList());

        imageUtils.deleteImage(deletePatchList);

        itemRepository.delete(findItem);
    }
    public Item findVerifiedItem(Long itemId) {
        Optional<Item> optionalItem = itemRepository.findByItemId(itemId);
        return optionalItem.orElseThrow(() -> new BusinessLogicException(CommonExceptionCode.ITEM_NOT_FOUND));
    }

    // 아이템 검색
    public Page<Item> searchItem(String itemName, Pageable pageable) {
        if(itemName == null) {
            itemName = "";
        }
        Pageable pageRequest = PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(), Sort.by("itemId"));

        return itemRepository.findByItemNameContainingIgnoreCase(itemName, pageRequest);
    }


    // 아이템 최신순 정렬
    private Page<Item> findItemByPageRequest(Pageable pageable) {
        Pageable pageRequest = PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(),Sort.by("itemId").descending());

        return itemRepository.findAll(pageRequest);
    }

    // 카테고리 조회
    public Page<Item> findItemByCategory(Category category, Pageable pageable) {
        Pageable pageRequest = PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(),Sort.by("itemId").descending());

        return itemRepository.findItemByCategory(category, pageRequest);
    }

    // 높은 가격순 정렬
    public Page<Item> findItemByHighPrice(Pageable pageable) {
        Pageable pageRequest = PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(),Sort.by("itemPrice").descending());

        return itemRepository.findAll(pageRequest);
    }

    // 낮은 가격순 정렬
    public Page<Item> findItemByLowPrice(Pageable pageable) {
        Pageable pageRequest = PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(),Sort.by("itemPrice").ascending());

        return itemRepository.findAll(pageRequest);
    }





    // favorite item

//    public Item createFavorite(Long itemId) {
//    }
//
//    public Item cancleFavorite(Long itemId) {
//    }
}
