package project.main.webstore.domain.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.item.entity.ItemOption;
import project.main.webstore.domain.item.repository.OptionRepository;
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

        return optionRepository.save(itemOption);
    }

    public ItemOption editOption(ItemOption itemOption) {
        ItemOption findOption= findVerifiedOption(itemOption.getOptionId());
        findOption.setOptionDetail(itemOption.getOptionDetail());
        return optionRepository.save(findOption);
    }

    public void deleteOption(Long optionId) {
        ItemOption findOption = findVerifiedOption(optionId);

        optionRepository.delete(findOption);
    }

    public ItemOption getOption(Long optionId) {
        return findVerifiedOption(optionId);
    }
    public Page<ItemOption> getOptions(Long itemId, Pageable pageable) {
        Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("specId"));
        Item findItem = itemService.validItem(itemId);
        String detail = findItem.getDetail();

        return optionRepository.findByDetail(detail, pageRequest);
    }
    public List<ItemOption> getOptions(Long itemId) {
        return optionRepository.findAllByItemId(itemId);
    }
    private ItemOption findVerifiedOption (Long optionId) {
        Optional<ItemOption> optionalItemOption = optionRepository.findById(optionId);
        return optionalItemOption.orElseThrow(() -> new BusinessLogicException(CommonExceptionCode.OPTION_NOT_FOUND));
    }
}
