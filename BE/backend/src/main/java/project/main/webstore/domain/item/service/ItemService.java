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
        itemValidService.validItemExist(item);

        return itemRepository.save(item);
    }

    public Item postItem(Item item, List<ImageInfoDto> imageInfoList) {
        itemValidService.validItemExist(item);

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
    public Item getItem(Long itemId) {
        Item item = itemValidService.validItem(itemId);

        item.addViewCount();
        return item;
    }
    // 아이템 검색

    public Page<Item> searchItem(String keyword, Pageable pageable) {
        if(keyword == null) {
            return itemRepository.findAll(pageable);
        }
        Page<Item> findItem = itemRepository.findByItemNameContainingIgnoreCase(keyword, pageable);
        return findItem;
    }
    //TODO: 스펙 검색, 이름 검색 모두 작업 진행이 가능하다. 다만 두개 모두 페이징 처리 해서 가져오는 방식은 일단 불가능하다. DB에서 가져올 때 페이징 처리를 하고, 이를 JAVA 단에서 합쳐서 페이징 처리한다는 것 자체가 조금은 이상한 것 같다.
    //요청이 올때마다 서버에서 DB에서 데이터 뒤져서 가져오고 이걸 다시 합치고 페이징 처리하는 일련의 과정이 번거롭고 불필요하다고 생각이 들었다. 서버에서 한방에 모든 데이터를 넘겨주고 프론트에서 제어하는 방법 등등이 효율적인 것 같다.
    public Page<Item> searchItemBySpec(String keyword,Pageable pageable){
        if(keyword == null) {
            return itemRepository.findAll(pageable);
        }
        Page<ItemSpec> findSpec = specRepository.findByKeyword(keyword, pageable);
        Page<Item> trans = findSpec.map(ItemSpec::getItem);
        return trans;
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

}
