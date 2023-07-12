package project.main.webstore.domain.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.main.webstore.domain.item.dto.ItemPostDto;
import project.main.webstore.domain.item.dto.ItemResponseDto;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.item.entity.ItemOption;
import project.main.webstore.domain.item.entity.ItemSpec;
import project.main.webstore.domain.item.repository.ItemRepository;
import project.main.webstore.domain.item.repository.SpecRepository;
import project.main.webstore.exception.BusinessLogicException;
import project.main.webstore.exception.CommonExceptionCode;

import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class SpecService {
    private final SpecRepository specRepository;
    private final ItemService itemService;

    // TODO: itemSpec
    public ItemSpec writeSpec(ItemSpec itemSpec, Long itemId) {
        Item findItem = itemService.findVerifiedItem(itemId);
        itemSpec.setItem(findItem);

        return specRepository.save(itemSpec);
    }

    public ItemSpec editSpec(ItemSpec itemSpec) {
        ItemSpec findSpec = findVerifiedSpec(itemSpec.getSpecId());
        findSpec.setContent(itemSpec.getContent());
        return specRepository.save(findSpec);
    }

    public void deleteSpec(Long specId) {
        ItemSpec findSpec = findVerifiedSpec(specId);

        specRepository.delete(findSpec);
    }

    public ItemSpec getSpec(Long specId) {
        return findVerifiedSpec(specId);
    }

    public Page<ItemSpec> getSpecs(Long itemId, Pageable pageable) {
        Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("specId"));
        Item findItem = itemService.findVerifiedItem(itemId);
        String specs = findItem.getSpecs();

        return specRepository.findBySpecs(specs, pageRequest);
    }

    public ItemSpec findVerifiedSpec(Long specId) {
        Optional<ItemSpec> optionalItemSpec = specRepository.findById(specId);
        return optionalItemSpec.orElseThrow(() -> new BusinessLogicException(CommonExceptionCode.SPEC_NOT_FOUND));
    }
}
