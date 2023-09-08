package project.main.webstore.domain.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.main.webstore.domain.image.dto.ImageInfoDto;
import project.main.webstore.domain.image.entity.Image;
import project.main.webstore.domain.image.entity.ItemImage;
import project.main.webstore.domain.image.utils.ImageUtils;
import project.main.webstore.domain.item.dto.PickedItemDto;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.item.entity.ItemOption;
import project.main.webstore.domain.item.entity.PickedItem;
import project.main.webstore.domain.item.enums.Category;
import project.main.webstore.domain.item.enums.ItemStatus;
import project.main.webstore.domain.item.repository.ItemRepository;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.domain.users.service.UserValidService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemValidService itemValidService;
    private final UserValidService userValidService;
    private final ImageUtils imageUtils;

    // 기존 등록된 item 검증 후 등록
    public Item postItem(Item item) {

        return itemRepository.save(item);
    }

    public Item postItem(Item item, List<ImageInfoDto> imageInfoList) {

        List<Image> images = imageUtils.uploadImageList(imageInfoList);
        List<ItemImage> imageList = images.stream().map(image -> new ItemImage(image, item)).collect(Collectors.toList());
        item.setItemImageList(imageList);

        return itemRepository.save(item);
    }

    public Item patchItem(List<ImageInfoDto> imageInfoDtoList, List<Long> deleteImageId, Item item, List<Long> deleteOptionIdList) {
        Item findItem = itemValidService.validItem(item.getItemId());

        changItemValue(item, findItem);
        //삭제 먼저
        deleteOption(findItem, deleteOptionIdList);
        //옵션 변경
        addOptionList(findItem, item.getOptionList());

        if (imageInfoDtoList != null) {
            changImage(imageInfoDtoList, deleteImageId, item, findItem);
        }
        return findItem;
    }

    private void addOptionList(Item findItem, List<ItemOption> patchOptionList) {
        List<ItemOption> findOptionList = findItem.getOptionList();
        List<ItemOption> addOptionList = separateList(patchOptionList);

        changeOptionValue(patchOptionList, findOptionList);

        for (ItemOption addOption : addOptionList) {
            findOptionList.add(addOption);
            addOption.setItem(findItem);
        }
    }

    private void changeOptionValue(List<ItemOption> patchOptionList, List<ItemOption> findOptionList) {
        for (ItemOption findOption : findOptionList) {
            for (ItemOption patchOption : patchOptionList) {
                if (patchOption.getOptionId().equals(findOption.getOptionId())) {
                    changOptionValue(findOption, patchOption);
                }
            }
        }
    }

    private List<ItemOption> separateList(List<ItemOption> patchOptionList) {
        ArrayList<ItemOption> addOptionList = new ArrayList<>();
        for (ItemOption itemOption : patchOptionList) {
            if (itemOption.getOptionId() == null) {
                addOptionList.add(itemOption);
                patchOptionList.remove(itemOption);
            }
        }
        return addOptionList;
    }


    //TODO : 간소화 작업 필요
    private void deleteOption(Item findItem, List<Long> deleteOptionIdList) {
        List<ItemOption> findOption = findItem.getOptionList();
        for (ItemOption option : findOption) {
            for (Long optionId : deleteOptionIdList) {
                if (option.getOptionId().equals(optionId)) {
                    findOption.remove(option);
                }
            }
        }
    }

    private void changImage(List<ImageInfoDto> imageInfoDtoList, List<Long> deleteImageId, Item item, Item findItem) {
        imageUtils.imageValid(imageInfoDtoList);
        List<Image> imageList = imageUtils.patchImage(imageInfoDtoList, findItem.getItemImageList(), deleteImageId);
        if (imageList.isEmpty() == false) {
            imageList.stream().map(image -> new ItemImage(image, item)).forEach(findItem::addItemImage);
        }
    }

    private void changItemValue(Item item, Item findItem) {
        Optional.ofNullable(item.getCategory()).ifPresent(findItem::setCategory);
        Optional.ofNullable(item.getItemName()).ifPresent(findItem::setItemName);
        Optional.ofNullable(item.getDiscountRate()).ifPresent(findItem::setDiscountRate);
        Optional.ofNullable(item.getDescription()).ifPresent(findItem::setDescription);
        Optional.ofNullable(item.getDefaultItem()).ifPresent(findItem::setDefaultItem);
        Optional.ofNullable(item.getItemPrice()).ifPresent(findItem::setItemPrice);
        Optional.ofNullable(item.getDeliveryPrice()).ifPresent(findItem::setDeliveryPrice);
        if (findItem.getDefaultItem().getItemCount() > 0) {
            item.setItemStatus(ItemStatus.ON_STACK);
        } else {
            item.setItemStatus(ItemStatus.SOLD_OUT);
        }
    }

    private void changOptionValue(ItemOption find, ItemOption patch) {
        Optional.ofNullable(patch.getOptionDetail()).ifPresent(find::setOptionDetail);
        Optional.ofNullable(patch.getOptionName()).ifPresent(find::setOptionName);
        Optional.ofNullable(patch.getAdditionalPrice()).ifPresent(find::setAdditionalPrice);
        Optional.ofNullable(patch.getItemCount()).ifPresent(find::setItemCount);
    }


    public void deleteItem(Long itemId) {
        Item findItem = itemValidService.validItem(itemId);

        List<ItemImage> itemImageList = findItem.getItemImageList();
        if (!itemImageList.isEmpty()) {
            List<String> deletePatchList = itemImageList.stream().map(ItemImage::getImagePath).collect(Collectors.toList());
            imageUtils.deleteImage(deletePatchList);
        }
        itemRepository.delete(findItem);
    }

    public Item getItem(Long itemId, Long userId) {
        Item item = itemValidService.validItem(itemId);
        if (!userId.equals(-1L)) {
            User user = userValidService.validUser(userId);
            List<PickedItem> pickedItemList = user.getPickedItemList();
            for (PickedItem pickedItem : pickedItemList) {
                if (pickedItem.getItem().getItemId().equals(item.getItemId())) {
                    item.setLike(true);
                }
            }
        }
        item.addViewCount();

        return item;
    }
    // 아이템 검색

    public Page<Item> searchItem(String keyword, Pageable pageable, Long userId) {
        if (keyword == null) {
            return itemRepository.findAll(pageable);
        }
        Page<Item> findItem = itemRepository.findByItemNameContainingIgnoreCase(keyword, pageable);

        if (!userId.equals(-1L)) {
            User user = userValidService.validUser(userId);
            List<PickedItem> pickedItemList = user.getPickedItemList();
            transLike(pickedItemList, findItem);
        }

        return findItem;
    }

    // 카테고리 조회
    public Page<Item> findItemByCategory(Category category, Pageable pageable, Long userId) {
        Page<Item> findItem = itemRepository.findItemByCategory(category, pageable);
        if (!userId.equals(-1L)) {
            User user = userValidService.validUser(userId);
            List<PickedItem> pickedItemList = user.getPickedItemList();
            transLike(pickedItemList, findItem);
        }
        return findItem;
    }
    // 높은 가격순 정렬

    public Page<Item> findItemPage(Pageable pageable, Long userId) {
        Page<Item> findItem = itemRepository.findAll(pageable);
        if (!userId.equals(-1L)) {
            User user = userValidService.validUser(userId);
            List<PickedItem> pickedItemList = user.getPickedItemList();
            transLike(pickedItemList, findItem);
        }
        return findItem;
    }

    public PickedItemDto pickItem(Long itemId, Long userId) {
        Item find = itemValidService.validItem(itemId);
        User findUser = userValidService.validUser(userId);
        List<PickedItem> userPickedItem = findUser.getPickedItemList();
        List<PickedItem> pickedItemList = userPickedItem;
        List<PickedItem> itemPickedItem = find.getPickedItem();

        boolean flag = pickedItemList.stream().map(pickedItem -> pickedItem.getItem().getItemId()).anyMatch(id -> id.equals(itemId));
        PickedItemDto result = new PickedItemDto(userId, itemId);
        //찜취소
        if (flag) {
            for (PickedItem pickedItem : userPickedItem) {
                if (pickedItem.getItem().getItemId().equals(itemId)) {
                    userPickedItem.remove(pickedItem);
                    itemPickedItem.remove(pickedItem);
                    result.setPicked(false);
                    break;
                }
            }
        } else {
            PickedItem pickedItem = new PickedItem(find, findUser);
            pickedItemList.add(pickedItem);
            userPickedItem.add(pickedItem);
            result.setPicked(true);
        }
        return result;
    }

    private void transLike(List<PickedItem> pickedItemList, Page<Item> findItem) {
        for (PickedItem pickedItem : pickedItemList) {
            Long itemId = pickedItem.getItem().getItemId();
            for (Item item : findItem.getContent()) {
                if (item.getItemId().equals(itemId)) {
                    item.setLike(true);
                }
            }
        }
    }

    public List<Item> getPickedItem(Long userId) {
        User findUser = userValidService.validUser(userId);
        List<PickedItem> list = findUser.getPickedItemList();
        if (list == null) {
            return null;
        }
        List<Item> result = list.stream().map(PickedItem::getItem).peek(item -> item.setLike(true)).collect(Collectors.toList());
        return result;
    }
}
