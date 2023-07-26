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
import project.main.webstore.domain.item.dto.PickedItemDto;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.item.entity.PickedItem;
import project.main.webstore.domain.item.enums.Category;
import project.main.webstore.domain.item.repository.ItemRepository;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.domain.users.service.UserValidService;
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
    private final UserValidService userValidService;
    private final FileUploader fileUploader;
    private final ImageUtils imageUtils;

    // 기존 등록된 item 검증 후 등록
    public Item postItem(Item item) {
        validItemExist(item);

        return itemRepository.save(item);
    }

    public Item postItem(Item item, List<ImageInfoDto> imageInfoList) {
        validItemExist(item);
        imageUtils.imageValid(imageInfoList);

        List<Image> images = fileUploader.uploadImage(imageInfoList);
        List<ItemImage> imageList = images.stream().map(image -> new ItemImage(image, item)).collect(Collectors.toList());
        item.setItemImageList(imageList);

        return itemRepository.save(item);
    }

    public Item patchItem(List<ImageInfoDto> imageInfoDtoList, List<Long> deleteImageId, Item item) {
        Item findItem = validItem(item.getItemId());

        Optional.ofNullable(item.getCategory()).ifPresent(findItem::setCategory);
        Optional.ofNullable(item.getItemName()).ifPresent(findItem::setItemName);
        Optional.ofNullable(item.getDiscountRate()).ifPresent(findItem::setDiscountRate);
        Optional.ofNullable(item.getDescription()).ifPresent(findItem::setDescription);
        Optional.ofNullable(item.getDefaultItem()).ifPresent(findItem::setDefaultItem);
        Optional.ofNullable(item.getItemPrice()).ifPresent(findItem::setItemPrice);
        Optional.ofNullable(item.getDeliveryPrice()).ifPresent(findItem::setDeliveryPrice);
        Optional.ofNullable(item.getMileageRate()).ifPresent(findItem::setMileageRate);
        if(imageInfoDtoList != null){
            imageUtils.imageValid(imageInfoDtoList);
            List<Image> imageList = imageUtils.patchImage(imageInfoDtoList, findItem.getItemImageList(), deleteImageId);
            if (imageList.isEmpty() == false) {
                imageList.stream().map(image -> new ItemImage(image, item)).forEach(findItem::addItemImage);
            }
        }
        return findItem;
    }

    public void deleteItem(Long itemId) {
        Item findItem = validItem(itemId);

        // TODO:
        List<ItemImage> itemImageList = findItem.getItemImageList();
        List<String> deletePatchList = itemImageList.stream().map(ItemImage::getImagePath).collect(Collectors.toList());

        imageUtils.deleteImage(deletePatchList);

        itemRepository.delete(findItem);
    }
    public Item validItem(Long itemId) {
        return itemRepository
                .findByItemId(itemId)
                .orElseThrow(() -> new BusinessLogicException(CommonExceptionCode.ITEM_NOT_FOUND));

    }
    // 아이템 검색

    public Page<Item> searchItem(String itemName, Pageable pageable) {
        if(itemName == null) {
            return itemRepository.findAll(pageable);
        }

        return itemRepository.findByItemNameContainingIgnoreCase(itemName, pageable);
    }

    // 아이템 최신순 정렬

    private Page<Item> findItemByPageRequest(Pageable pageable) {
        Pageable pageRequest = PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(),Sort.by("itemId").descending());

        return itemRepository.findAll(pageRequest);
    }
    // 카테고리 조회

    public Page<Item> findItemByCategory(Category category, Pageable pageable) {
        return itemRepository.findItemByCategory(category, pageable);
    }
    // 높은 가격순 정렬

    public Page<Item> findItemPage(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }
    public PickedItemDto pickItem(Long itemId, Long userId) {
        Item find = validItem(itemId);
        User findUser = userValidService.validUser(userId);
        List<PickedItem> userPickedItem = findUser.getPickedItemList();
        List<PickedItem> pickedItemList = userPickedItem;
        List<PickedItem> itemPickedItem = find.getPickedItem();

        boolean flag = pickedItemList.stream().map(pickedItem -> pickedItem.getItem().getItemId()).anyMatch(id -> id == itemId);
        PickedItemDto result = new PickedItemDto(userId, itemId);
        //찜취소
        if(flag){
            for (PickedItem pickedItem : userPickedItem) {
                if(pickedItem.getItem().getItemId() == itemId){
                    userPickedItem.remove(pickedItem);
                    itemPickedItem.remove(pickedItem);
                    result.setPicked(false);
                }
            }
        }else{
            PickedItem pickedItem = new PickedItem(find, findUser);
            pickedItemList.add(pickedItem);
            userPickedItem.add(pickedItem);
            result.setPicked(true);
        }
        return result;
    }

    private void validItemExist(Item item) {
        Optional<Item> findItem = itemRepository.findByItemId(item.getItemId());
        if(findItem.isPresent()){
            throw new BusinessLogicException(CommonExceptionCode.ITEM_EXIST);
        }
    }
}
