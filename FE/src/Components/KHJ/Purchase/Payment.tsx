import { AiFillCreditCard, AiFillIdcard } from 'react-icons/ai';
import { GrMoney } from 'react-icons/gr';

export default function Payment() {
  const iconSize: number = 22;
  const paymentList = [
    {
      text: '카드결제',
      icon: <AiFillCreditCard size={iconSize} />,
      id: 'card',
    },
    { text: '계좌이체', icon: <AiFillIdcard size={iconSize} />, id: 'account' },
    { text: '가상계좌', icon: <GrMoney size={iconSize} />, id: 'bank' },
    { text: '페이코', icon: 'PAYCO', id: 'payco' },
  ];
  return (
    <section className="flex mx-5 items-center py-7 border-y border-gray-300">
      <h2 className="w-36">결제수단</h2>
      <ul className="flex">
        {paymentList.map((list) => (
          <li key={list.text} className="mr-4">
            <input
              type="radio"
              name="payment"
              id={list.id}
              defaultChecked={list.id === 'card' && true}
              className="peer hidden"
            ></input>
            <label
              htmlFor={list.id}
              className="flex flex-col justify-center items-center border rounded-xl border-gray-300 w-24 h-16 py-0.5 box-content peer-checked:border-black peer-checked:font-bold cursor-pointer"
            >
              <span className="font-bold">{list.icon}</span>
              {list.text}
            </label>
          </li>
        ))}
      </ul>
    </section>
  );
}
