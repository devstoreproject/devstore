package project.main.webstore.domain.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.item.repository.ItemRepository;
import project.main.webstore.exception.BusinessLogicException;
import project.main.webstore.exception.CommonExceptionCode;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemValidService {
    private final ItemRepository itemRepository;

    public Item validItem(Long id){
        return itemRepository
                .findByItemId(id)
                .orElseThrow(() -> new BusinessLogicException(CommonExceptionCode.ITEM_NOT_FOUND));
    }
    protected void validItemExist(Item item) {
        Optional<Item> findItem = itemRepository.findByItemId(item.getItemId());
        if(findItem.isPresent()){
            throw new BusinessLogicException(CommonExceptionCode.ITEM_EXIST);
        }
    }
}
