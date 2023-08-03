package project.main.webstore.domain.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.item.entity.ItemOption;
import project.main.webstore.domain.item.repository.OptionRepository;
import project.main.webstore.domain.order.exception.OrderExceptionCode;
import project.main.webstore.exception.BusinessLogicException;
import project.main.webstore.exception.CommonExceptionCode;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class OptionService {
    private final OptionRepository optionRepository;
    private final ItemService itemService;

    public ItemOption writeOption(ItemOption itemOption, Long itemId) {
        Item findItem = itemService.validItem(itemId);
        itemOption.setItem(findItem);
        findItem.getOptionList().add(itemOption);

        return optionRepository.save(itemOption);
    }

    public ItemOption editOption(ItemOption itemOption) {
        ItemOption findOption= findVerifiedOption(itemOption.getOptionId());
        Optional.ofNullable(itemOption.getItemCount()).ifPresent(findOption::setItemCount);
        Optional.ofNullable(itemOption.getOptionDetail()).ifPresent(findOption::setOptionDetail);
        return findOption;
    }

    public void deleteOption(Long optionId) {
        ItemOption findOption = findVerifiedOption(optionId);
        List<ItemOption> optionList = findOption.getItem().getOptionList();
        for (ItemOption itemOption : optionList) {
            if(itemOption.getOptionId() == optionId)
                optionList.remove(itemOption);
                break;
        }
        optionRepository.delete(findOption);
    }

    public ItemOption getOption(Long optionId) {
        return findVerifiedOption(optionId);
    }
    public List<ItemOption> getOptions(List<Long> optionId){
        return optionRepository.findInId(optionId);
    }
    public List<ItemOption> getOptions(Long itemId) {
        return optionRepository.findAllByItemId(itemId);
    }
    private ItemOption findVerifiedOption (Long optionId) {
        Optional<ItemOption> optionalItemOption = optionRepository.findById(optionId);
        return optionalItemOption.orElseThrow(() -> new BusinessLogicException(OrderExceptionCode.OPTION_NOT_FOUND));
    }
}
