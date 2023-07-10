import { AiFillCreditCard } from 'react-icons/ai';

export default function TotalPrice() {
  const cost = [
    {
      info: '주문금액',
      cost: '416,000',
    },
    {
      info: '할인',
      cost: '-18,000',
    },
    {
      info: '배송비',
      cost: '0',
    },
    {
      info: '적립금 사용',
      cost: '-700',
    },
    {
      info: '적립금',
      cost: '1,400',
    },
    // {
    //   info: '최종결제금액',
    //   cost: '398,000',
    // },
  ];
  return (
    <section className="mx-5 flex justify-between pb-7 border-b border-gray-300 mb-16">
      <div className="flex justify-between flex-wrap">
        <h2 className="w-14 mt-7">결제</h2>
        <p className="flex items-center h-6 whitespace-nowrap mt-7">
          <AiFillCreditCard /> 카드 결제 (1234-1234-****-****) 일시불
        </p>
        <div className="mt-4 w-80">
          {cost.map((info) => (
            <p key={info.info} className="mt-3 flex justify-between last:mt-5">
              <span>{info.info}</span>
              <em>{info.cost}</em>
            </p>
          ))}
        </div>
        <div className="flex justify-end w-full mt-3 border-t border-gray-300">
          <p className="mt-3 flex justify-between w-80">
            <span>최종결제금액</span>
            <strong className="text-2xl">398,000</strong>
          </p>
        </div>
      </div>
      <div className="mt-auto text-right">
        <p>
          <input type="radio" className="mr-2" />
          주문 내역을 확인하고 결제가 진행되는 것에 동의합니다.
        </p>
        <button className="mt-3 w-60 h-14 bg-light-black rounded-full text-xl text-white">
          결제하기
        </button>
      </div>
    </section>
  );
}
