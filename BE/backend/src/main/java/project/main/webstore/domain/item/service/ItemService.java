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
import project.main.webstore.domain.item.entity.ItemSpec;
import project.main.webstore.domain.item.entity.PickedItem;
import project.main.webstore.domain.item.enums.Category;
import project.main.webstore.domain.item.repository.ItemRepository;
import project.main.webstore.domain.item.repository.SpecRepository;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.domain.users.service.UserValidService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final SpecRepository specRepository;
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

    public Item patchItem(List<ImageInfoDto> imageInfoDtoList, List<Long> deleteImageId, Item item) {
        Item findItem = itemValidService.validItem(item.getItemId());

        Optional.ofNullable(item.getCategory()).ifPresent(findItem::setCategory);
        Optional.ofNullable(item.getItemName()).ifPresent(findItem::setItemName);
        Optional.ofNullable(item.getDiscountRate()).ifPresent(findItem::setDiscountRate);
        Optional.ofNullable(item.getDescription()).ifPresent(findItem::setDescription);
        Optional.ofNullable(item.getDefaultItem()).ifPresent(findItem::setDefaultItem);
        Optional.ofNullable(item.getItemPrice()).ifPresent(findItem::setItemPrice);
        Optional.ofNullable(item.getDeliveryPrice()).ifPresent(findItem::setDeliveryPrice);

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
        Item findItem = itemValidService.validItem(itemId);

        List<ItemImage> itemImageList = findItem.getItemImageList();
        if(!itemImageList.isEmpty()) {
            List<String> deletePatchList = itemImageList.stream().map(ItemImage::getImagePath).collect(Collectors.toList());
            imageUtils.deleteImage(deletePatchList);
        }
        itemRepository.delete(findItem);
    }
    public Item getItem(Long itemId, Long userId) {
        Item item = itemValidService.validItem(itemId);
        if(!userId.equals(-1L)){
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
        if(keyword == null) {
            return itemRepository.findAll(pageable);
        }
        Page<Item> findItem = itemRepository.findByItemNameContainingIgnoreCase(keyword, pageable);

        if (!userId.equals(-1L)) {
            User user = userValidService.validUser(userId);
            List<PickedItem> pickedItemList = user.getPickedItemList();
            transLike(pickedItemList,findItem);
        }

        return findItem;
    }


    public Page<Item> searchItemBySpec(String keyword,Pageable pageable,Long userId){
        if(keyword == null) {
            return itemRepository.findAll(pageable);
        }
        Page<ItemSpec> findSpec = specRepository.findByKeyword(keyword, pageable);
        Page<Item> trans = findSpec.map(ItemSpec::getItem);
        if (!userId.equals(-1L)) {
            User user = userValidService.validUser(userId);
            List<PickedItem> pickedItemList = user.getPickedItemList();
            transLike(pickedItemList,trans);
        }
        return trans;
    }
    // 카테고리 조회
    public Page<Item> findItemByCategory(Category category, Pageable pageable, Long userId) {
        Page<Item> findItem = itemRepository.findItemByCategory(category, pageable);
        if (!userId.equals(-1L)) {
            User user = userValidService.validUser(userId);
            List<PickedItem> pickedItemList = user.getPickedItemList();
            transLike(pickedItemList,findItem);
        }
        return findItem;
    }
    // 높은 가격순 정렬

    public Page<Item> findItemPage(Pageable pageable, Long userId) {
        Page<Item> findItem = itemRepository.findAll(pageable);
        if (!userId.equals(-1L)) {
            User user = userValidService.validUser(userId);
            List<PickedItem> pickedItemList = user.getPickedItemList();
            transLike(pickedItemList,findItem);
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
}
