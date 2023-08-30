import api from 'api';
import CartItem from './CartItem';
import { useEffect, useState } from 'react';

interface cartInfoType {
  cartId: number;
  deliveryPrice: number;
  discountedPrice: number;
  itemList: [];
  totalPrice: number;
  userId: number;
}

export default function CartMain() {
  const [isCart, setIsCart] = useState<any[]>([]);
  const [isCartInfo, setIsCartInfo] = useState<cartInfoType>();
  const userId = Number(localStorage.getItem('userId'));
  // 장바구니 가져오기
  const getCart = (userId: number) => {
    api
      .get(`api/cart/users/${userId}`)
      .then((res) => {
        const cartItems = res.data.data.itemList;
        const cart = res.data.data;
        setIsCart(cartItems);
        setIsCartInfo(cart);
      })
      .catch((err) => {
        // 장바구니가 빌 경우 404에러가 발생, 카트가 초기화 되지 않는 오류가 생긴다. 해당 문제를 해결하기 위해 아래 에러 핸들링 코드를 추가
        if (err.code === 'ERR_BAD_REQUEST') {
          setIsCart([]);
        }
      });
  };
  // 가격에 콤마
  const regexComma = /\B(?=(\d{3})+(?!\d))/g;
  const discountTotal = isCartInfo?.discountedPrice
    .toString()
    .replace(regexComma, ',');
  const deliveryTotal = isCartInfo?.deliveryPrice
    .toString()
    .replace(regexComma, ',');
  const allPrice = () => {
    if (isCartInfo !== undefined) {
      const result = isCartInfo.discountedPrice + isCartInfo.deliveryPrice;
      return result.toString().replace(regexComma, ',');
    }
  };
  useEffect(() => {
    getCart(userId);
  }, [userId]);
  if (isCart.length !== 0) {
    return (
      <section className="mx-5">
        {/* <input type="checkbox" className="w-5 h-5 ml-8 mb-4"></input> */}
        <div className="rounded-xl bg-gray-100 border-gray-300 border p-8">
          <ul>
            {isCart.map((item) => (
              <CartItem
                item={item}
                key={item.optionId}
                userId={userId}
                getCart={getCart}
              />
            ))}
          </ul>
        </div>
        <div className="flex justify-between items-center mt-6">
          <div className="flex">
            <p>주문금액 {discountTotal}</p>
            <p className="ml-6">배송비 {deliveryTotal}</p>
          </div>
          <div className="flex items-center">
            <p className="flex items-center">
              총합
              <strong className="ml-2 text-2xl">{allPrice()}</strong>
            </p>
            <button className="ml-4 w-60 h-14 bg-light-black rounded-full text-xl text-white">
              구매하기
            </button>
          </div>
        </div>
      </section>
    );
  } else {
    return (
      <section className="mx-5">
        <div className="rounded-xl bg-gray-100 border-gray-300 border p-8">
          장바구니가 비어있습니다.
        </div>
        <div className="flex justify-end items-center mt-6">
          <div className="flex items-center">
            <button
              className="ml-4 w-60 h-14 bg-light-black rounded-full text-xl text-white opacity-30"
              disabled
            >
              구매하기
            </button>
          </div>
        </div>
      </section>
    );
  }
}
