import { useState } from 'react';
import type { CartItemType } from 'Components/KHJ/Type/CartTypes';

interface CartItemProps {
  item: CartItemType;
}

function CartItem({ item }: CartItemProps): React.ReactElement {
  const [isOptOpen, setIsOptOpen] = useState(false);
  const [isQuantity, setIsQuantity] = useState(1);

  const handleOptOpen = () => {
    setIsOptOpen(!isOptOpen);
  };
  const handleQuantityDown = () => {
    if (isQuantity === 1) return;
    // 알람, 예 누르면 장바구니에서 삭제
    if (isQuantity < 2) return;
    setIsQuantity(isQuantity - 1);
  };
  const handleQuantityUp = () => {
    setIsQuantity(isQuantity + 1);
  };
  return (
    <li
      className={`flex items-center justify-between ${
        isOptOpen ? 'mb-48' : 'mb-10 last-of-type:mb-0'
      }`}
    >
      <input type="checkbox" className="w-5 h-5 border-gray-300"></input>
      <div className="rounded-xl w-36 h-36 bg-white"></div>
      <div className="w-3/5 relative">
        <div className="flex items-center justify-between">
          <div>
            <h3>{item.text}</h3>
            <div className="text-subtitle-gray">
              <span className="mr-4">색상 : {item.color}</span>
              <span>너비 : {item.option}</span>
            </div>
          </div>
          <div className="flex">
            <button
              className="w-7 h-7 rounded-full border border-gray-300"
              onClick={handleQuantityDown}
            >
              -
            </button>
            <p className="mx-6">{isQuantity}</p>
            <button
              className="w-7 h-7 rounded-full border border-gray-300"
              onClick={handleQuantityUp}
            >
              +
            </button>
            <button
              className={`px-4 h-7 rounded-full border ml-6 ${
                isOptOpen
                  ? 'border-light-black text-white bg-light-black'
                  : 'border-gray-300'
              }`}
              onClick={handleOptOpen}
            >
              옵션변경
            </button>
          </div>
        </div>
        <div
          className={`p-5 bg-white border-gray-300 rounded-xl border mt-6 absolute w-full z-10
            ${isOptOpen ? '' : 'hidden'}`}
        >
          <div className="relative h-14 w-full bg-white border-gray-300 mb-5 last-of-type:mb-0">
            <ul className="absolute h-14 w-full">
              <li className="flex items-center h-14 px-10 border border-gray-300 rounded-full text-subtitle-gray">
                색상 선택
              </li>
            </ul>
          </div>
          <div className="relative h-14 w-full bg-white border-gray-300 mb-5 last-of-type:mb-0">
            <ul className="absolute h-14 w-full">
              <li className="flex items-center h-14 px-10 border border-gray-300 rounded-full text-subtitle-gray">
                너비 선택
              </li>
            </ul>
          </div>
        </div>
      </div>
      <div className="relative">
        <span className="absolute bottom-3/4 text-subtitle-gray right-0">
          쿠폰적용가
        </span>
        <del className="text-subtitle-gray mr-2">416,000</del>
        <strong className="text-xl font-normal">398,000</strong>
      </div>
    </li>
  );
}

export default CartItem;
