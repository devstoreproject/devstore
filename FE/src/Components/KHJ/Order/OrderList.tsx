import { BsBoxSeam } from 'react-icons/bs';
import Button from '../Purchase/OrderBtn';
import { useState } from 'react';

export default function OrderList() {
  const [isOpen, setIsOpen] = useState(false);
  return (
    <section className="bg-gray-100 border border-gray-300 mx-5 rounded-xl">
      <table className="w-full rounded-xl">
        <thead>
          <tr className="text-center border-b border-gray-300">
            <th className="font-normal py-5" colSpan={3}>
              주문내역
            </th>
            <th className="font-normal">배송정보</th>
            <th className="font-normal">결제금액</th>
            <th className="font-normal"></th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td className="text-center">
              <input
                type="checkbox"
                className="w-5 h-5 border-gray-300"
              ></input>
            </td>
            <td className="text-center py-5">
              <div className="rounded-xl w-36 h-36 bg-white inline-block"></div>
            </td>
            <td>
              <h3>알파스캔 모니터</h3>
              <div className="text-subtitle-gray">
                <span className="mr-4">색상 : 화이트</span>
                <span>너비 : 24인치</span>
              </div>
            </td>
            <td className="text-center">
              <button>
                <BsBoxSeam size={20} />
              </button>
            </td>
            <td className="relative">
              <p className="text-xl font-normal text-center">398,000</p>
            </td>
            <td className="text-center">
              <Button
                btnName="교환∙반품요청"
                modalType="return"
                isOpen={isOpen}
                setIsOpen={setIsOpen}
              />
              <div className="h-2"></div>
              <Button btnName="리뷰작성" />
            </td>
          </tr>
        </tbody>
      </table>
    </section>
  );
}
