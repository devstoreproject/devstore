package project.main.webstore.domain.users.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.main.webstore.domain.order.exception.OrderExceptionCode;
import project.main.webstore.domain.users.entity.ShippingInfo;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.domain.users.repository.ShippingRepository;
import project.main.webstore.domain.users.repository.UserRepository;
import project.main.webstore.exception.BusinessLogicException;
import project.main.webstore.exception.CommonExceptionCode;

import javax.validation.constraints.Positive;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ShippingService {
    private final ShippingRepository shippingRepository;
    private final UserValidService userValidService;

    public ShippingInfo writeInfo(ShippingInfo info, Long userId) {
        User findUser = userValidService.validUser(userId);
        info.setUser(findUser);
        return shippingRepository.save(info);
    }

    public ShippingInfo editInfo(ShippingInfo info, Long userId) {
        User findUser = userValidService.validUser(userId);
        ShippingInfo findInfo = findVerifiedInfo(info.getInfoId());

        Optional.ofNullable(info.getRecipient()).ifPresent(findInfo::setRecipient);
        Optional.ofNullable(info.getAddress()).ifPresent(findInfo::setAddress);

        findInfo.setUser(findUser);

        return findInfo;
    }

    public ShippingInfo getInfo(Long infoId) {
        return findVerifiedInfo(infoId);
    }

    public void deleteInfo(Long infoId, Long userId) {
        User findUser = userValidService.validUser(userId);
        ShippingInfo findInfo = findVerifiedInfo(infoId);
        findInfo.setUser(findUser);
        shippingRepository.delete(findInfo);
    }
    private ShippingInfo findVerifiedInfo(Long infoId) {
        Optional<ShippingInfo> findByInfoId = shippingRepository.findByInfoId(infoId);
        return findByInfoId.orElseThrow(() -> new BusinessLogicException(OrderExceptionCode.SHIPPING_INFO_NOT_FOUND));
    }
}
