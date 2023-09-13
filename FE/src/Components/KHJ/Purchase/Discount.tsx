import { useState } from 'react';

const Discount: React.FC = () => {
  const [isOpen, setIsOpen] = useState(false);
  const handleOpen = () => {
    setIsOpen(!isOpen);
  };
  return (
    <section className="mx-5 py-7 border-t border-gray-300">
      <div>
        <div className="flex items-center">
          <h2 className="w-36">할인</h2>
          <p className="w-32">쿠폰 적용</p>
          <div className="relative w-136 h-14 select-none">
            <ul
              className={`border px-10 border-gray-300 bg-light-gray  absolute w-full ${
                isOpen ? 'rounded-xl' : 'overflow-hidden rounded-full h-14'
              }`}
              onClick={handleOpen}
            >
              <li className="h-14 flex items-center">
                18,000 할인 / 50,000 이상 구매 시 5% 할인 (신규혜택) ~2023.06.16
              </li>
              <li className={`h-14 flex items-center z-10 bg-light-gray`}>
                18,000 할인 / 50,000 이상 구매 시 5% 할인 (신규혜택) ~2023.06.16
              </li>
              <li className={`h-14 flex items-center z-10 bg-light-gray`}>
                18,000 할인 / 50,000 이상 구매 시 5% 할인 (신규혜택) ~2023.06.16
              </li>
            </ul>
          </div>
        </div>
        <div className="flex items-center ml-36">
          <p className="w-32">적립금 적용</p>
          <div className="flex mt-3">
            <p className="w-64 h-14 border border-gray-300 rounded-full px-10 bg-light-gray mr-2 flex items-center">
              1,500
            </p>
            <input
              type="number"
              className="w-64 h-14 border border-gray-300 rounded-full px-10 bg-light-gray mr-2 flex items-center appearance-none"
            ></input>
          </div>
        </div>
        <div className="ml-36">
          <p className="h-14 flex items-center ml-32 pl-10">700 할인 적용</p>
        </div>
      </div>
    </section>
  );
};

export default Discount;
