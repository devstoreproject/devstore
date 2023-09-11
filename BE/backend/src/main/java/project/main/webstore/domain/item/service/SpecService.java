package project.main.webstore.domain.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.item.entity.ItemSpec;
import project.main.webstore.domain.item.repository.SpecRepository;
import project.main.webstore.domain.order.exception.OrderExceptionCode;
import project.main.webstore.exception.BusinessLogicException;
import project.main.webstore.exception.CommonExceptionCode;

import java.util.List;
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
        Item findItem = itemService.validItem(itemId);
        itemSpec.setItem(findItem);

        return specRepository.save(itemSpec);
    }

    public ItemSpec editSpec(ItemSpec itemSpec) {
        ItemSpec findSpec = findVerifiedSpec(itemSpec.getSpecId());
        Optional.ofNullable(itemSpec.getName()).ifPresent(findSpec::setName);
        Optional.ofNullable(itemSpec.getContent()).ifPresent(findSpec::setContent);
        return specRepository.save(findSpec);
    }

    public void deleteSpec(Long specId) {
        ItemSpec findSpec = findVerifiedSpec(specId);
        List<ItemSpec> specList = findSpec.getItem().getSpecList();
        for (ItemSpec itemSpec : specList) {
            if(itemSpec.getSpecId() == specId){
                specList.remove(itemSpec);
                break;
            }
        }
        specRepository.delete(findSpec);
    }

    public ItemSpec getSpec(Long specId) {
        return findVerifiedSpec(specId);
    }

    public List<ItemSpec> getSpecs(Long itemId) {
        Item findItem = itemService.validItem(itemId);
        return findItem.getSpecList();
    }

    public ItemSpec findVerifiedSpec(Long specId) {
        Optional<ItemSpec> optionalItemSpec = specRepository.findById(specId);
        return optionalItemSpec.orElseThrow(() -> new BusinessLogicException(OrderExceptionCode.SPEC_NOT_FOUND));
    }
}
