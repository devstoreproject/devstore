import { AiFillCreditCard } from 'react-icons/ai';
import type { cartInfoType } from '../Type/CartTypes';
import { useState } from 'react';

interface payProps {
  requestPayment: () => void;
  isCartInfo?: cartInfoType;
  payGo: () => void;
  payGoShip: () => void;
  isMe: boolean;
  setIsMe: React.Dispatch<React.SetStateAction<boolean>>;
}

export default function PayProgress({
  requestPayment,
  isCartInfo,
  payGo,
  payGoShip,
  isMe,
  setIsMe,
}: payProps) {
  const [warningCheck, setWarningCheck] = useState<boolean>(false);
  const cost = [
    {
      info: '최종결제금액',
      cost: isCartInfo?.totalPrice,
    },
    {
      info: '배송비',
      cost: isCartInfo?.deliveryPrice,
    },
  ];

  return (
    <section className="mx-5 flex justify-between pb-7 border-b border-gray-300 mb-16 gap-5">
      <div className="flex justify-between gap-5">
        <h2 className="w-14 mt-7">결제</h2>
        <p className="flex items-center h-6 whitespace-nowrap mt-7">
          <AiFillCreditCard /> 카드 결제 일시불
        </p>
        <div className="mt-4 w-80">
          {cost.map((info) => (
            <p key={info.info} className="mt-3 flex justify-between last:mt-5">
              <span>{info.info}</span>
              <em>{info.cost}</em>
            </p>
          ))}
        </div>
      </div>
      <div className="mt-auto text-right">
        <p>
          <input
            type="radio"
            className="mr-2"
            onClick={() => {
              setWarningCheck(true);
            }}
          />
          주문 내역을 확인하고 결제가 진행되는 것에 동의합니다.
        </p>
        <button
          className="mt-3 w-60 h-14 bg-light-black rounded-full text-xl text-white"
          onClick={() => {
            if (!isMe && warningCheck) {
              payGo();
            } else if (isMe && warningCheck) {
              payGoShip();
            }
          }}
        >
          결제하기
        </button>
      </div>
    </section>
  );
}
