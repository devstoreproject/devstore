import PageHistory from 'Components/KHJ/Common/PageHistory';
import Delivery from 'Components/KHJ/Purchase/Delivery';
import PayProgress from 'Components/KHJ/Purchase/PayProgress';
import PurchaseListProps from 'Components/KHJ/Purchase/PurchaseListProps';
import type { cartInfoType } from 'Components/KHJ/Type/CartTypes';
import type { ShippingList } from 'Components/KHJ/Type/ShippingType';
import api from 'api';
import { useEffect, useState } from 'react';
import * as PortOne from '@portone/browser-sdk/v2';

export default function Purchase() {
  const userId = Number(localStorage.getItem('userId'));
  const [isCartInfo, setIsCartInfo] = useState<cartInfoType>();
  const [isMe, setIsMe] = useState(false);
  const [isShippingFormWrite, setIsShippingFormWrite] = useState<ShippingList>({
    recipient: '',
    mobileNumber: '',
    zipCode: '',
    addressSimple: '',
    addressDetail: '',
  });
  const getCart = (userId: number) => {
    api
      .get(`api/cart/users/${userId}`)
      .then((res) => {
        const cart = res.data.data;
        setIsCartInfo(cart);
      })
      .catch((err) => {
        // 장바구니가 빌 경우 404에러가 발생, 카트가 초기화 되지 않는 오류가 생긴다. 해당 문제를 해결하기 위해 아래 에러 핸들링 코드를 추가
        if (err.code === 'ERR_BAD_REQUEST') {
          setIsCartInfo(undefined);
        }
      });
  };

  useEffect(() => {
    getCart(userId);
  }, [userId]);

  const orderName = () => {
    if (isCartInfo !== undefined) {
      const nameArr = isCartInfo?.itemList.map((item) => item.itemName);
      return nameArr?.length > 1
        ? `${nameArr[0]} 외 ${nameArr?.length}개`
        : `${nameArr[0]}`;
    }
    return '';
  };
  // 주문번호
  const orderNumber = () => {
    return new Date().toISOString().replace(/[-T:.Z]/g, '');
  };

  // 결제
  const payGoShip = () => {
    const mobileNum = isShippingFormWrite.mobileNumber.replace(
      /(\d{3})(\d{4})(\d{4})/,
      '$1-$2-$3'
    );
    const prepareData = {
      merchant_uid: orderNumber(),
      amount: isCartInfo?.totalPrice,
    };
    const shipData = {
      recipient: isShippingFormWrite.recipient,
      mobileNumber: mobileNum,
      zipCode: isShippingFormWrite.zipCode,
      addressSimple: isShippingFormWrite.addressSimple,
      addressDetail: isShippingFormWrite.addressDetail,
    };
    api
      .post('api/payment/prepare', prepareData)
      .then(() => {
        api
          .post(`api/address`, shipData)
          .then((res) => {
            api
              .post('api/orders', {
                message: 'txdo3j7uQ4JFGlu2k9Mm',
                shippingInfoId: res.data.infoId,
              })
              // 결제완료 페이지로 params 보내며 이동
              .then(() => {})
              .catch((err) => {
                console.log(err);
              });
          })
          .catch((err) => {
            console.log(err);
          });
      })
      .catch((err) => {
        console.log(err);
      });
  };

  // 기존 배송지로 결제
  const payGo = () => {
    const prepareData = {
      merchant_uid: orderNumber(),
      amount: isCartInfo?.totalPrice,
    };
    const userId = Number(localStorage.getItem('userId'));
    api
      .post('api/payment/prepare', prepareData)
      .then(() => {
        api
          .get(`api/address/users/${userId}`)
          .then((res) => {
            api
              .post('api/orders', {
                message: 'txdo3j7uQ4JFGlu2k9Mm',
                shippingInfoId: res.data[0].infoId,
              })
              // 결제완료 페이지로 params 보내며 이동
              .then(() => {})
              .catch((err) => {
                console.log(err);
              });
          })
          .catch((err) => {
            console.log(err);
          });
      })
      .catch((err) => {
        console.log(err);
      });
  };

  // 결제모듈
  const requestPayment = () => {
    if (isCartInfo !== undefined) {
      PortOne.requestPayment({
        // 가맹점 storeId로 변경해주세요.
        storeId: '',
        paymentId: `paymentId_{now()}`,
        orderName: orderName(),
        totalAmount: isCartInfo.totalPrice,
        currency: 'CURRENCY_KRW',
        payMethod: 'CARD',
        channelKey: '',
      }).catch((error) => {
        console.log(error);
      });
    }
  };

  if (isCartInfo !== undefined && isCartInfo !== null) {
    return (
      <main>
        <PageHistory pageName="결제진행" pageHistory="결제진행" />
        <PurchaseListProps isCartInfo={isCartInfo} />
        <Delivery
          isShippingFormWrite={isShippingFormWrite}
          setIsShippingFormWrite={setIsShippingFormWrite}
          isMe={isMe}
          setIsMe={setIsMe}
        />
        <PayProgress
          requestPayment={requestPayment}
          isCartInfo={isCartInfo}
          payGo={payGo}
          payGoShip={payGoShip}
          isMe={isMe}
          setIsMe={setIsMe}
        />
      </main>
    );
  } else {
    return <PageHistory pageName="결제진행" pageHistory="결제진행" />;
  }
}
