import { useEffect, useState } from 'react';
import PurchaseItem from './PurchaseItem';
import api from 'api';
import type { cartInfoType } from '../Type/CartTypes';

export default function PurchaseList() {
  const userId = Number(localStorage.getItem('userId'));
  const [isCartInfo, setIsCartInfo] = useState<cartInfoType>();
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
  return (
    <section className="mx-5 border-b border-gray-300 pb-7">
      <table className="w-full text-center">
        <thead>
          <tr className="rounded-t-xl shadow-border h-14">
            <th>상품상세</th>
            <th>수량</th>
            <th>결제금액</th>
          </tr>
        </thead>
        <tbody>
          {isCartInfo?.itemList?.map((item) => (
            <PurchaseItem key={item.optionId} item={item} />
          ))}
        </tbody>
      </table>
    </section>
  );
}
