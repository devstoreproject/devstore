package project.main.webstore.domain.users.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.main.webstore.domain.order.exception.OrderExceptionCode;
import project.main.webstore.domain.users.entity.ShippingInfo;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.domain.users.repository.ShippingRepository;
import project.main.webstore.exception.BusinessLogicException;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ShippingService {
    private final ShippingRepository shippingRepository;
    private final UserValidService userValidService;

    public ShippingInfo postAddressInfo(ShippingInfo info, Long userId) {
        User findUser = userValidService.validUser(userId);
        findUser.addShipInfo(info);
        return info;
    }

    public ShippingInfo updateAddressInfo(ShippingInfo info, Long userId) {
        User findUser = userValidService.validUser(userId);
        ShippingInfo findInfo = validAddressInfo(info.getInfoId());

        Optional.ofNullable(info.getRecipient()).ifPresent(findInfo::setRecipient);
        Optional.ofNullable(info.getAddress()).ifPresent(findInfo::setAddress);

        findInfo.setUser(findUser);

        return findInfo;
    }

    public ShippingInfo getAddressInfo(Long infoId) {
        return validAddressInfo(infoId);
    }
    public List<ShippingInfo> getInfoList(Long userId) {
        User findUser = userValidService.validUser(userId);
        return findUser.getShippingInfoList();
    }

    public void deleteAddressInfo(Long infoId, Long userId) {
        User findUser = userValidService.validUser(userId);
        ShippingInfo findInfo = validAddressInfo(infoId);
        findInfo.setUser(findUser);
        shippingRepository.delete(findInfo);
    }
    private ShippingInfo validAddressInfo(Long infoId) {
        Optional<ShippingInfo> findByInfoId = shippingRepository.findByInfoId(infoId);
        return findByInfoId.orElseThrow(() -> new BusinessLogicException(OrderExceptionCode.SHIPPING_INFO_NOT_FOUND));
    }
}
