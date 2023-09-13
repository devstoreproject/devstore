import ResultTable from 'Components/CYW/OrderedList/ResultTable';
import useFetchOrders from 'hooks/mypage/useFetchOrders';
import { BsBoxSeam } from 'react-icons/bs';

export default function OrderedList() {
  const orders = useFetchOrders();
  return (
    <div className="flex flex-col w-full bg-light-gray">
      <div className="flex items-center mb-6">
        <BsBoxSeam size={25} />
        <h1 className="pl-2 text-xl font-bold">주문 된 상품 내역</h1>
      </div>
      <ResultTable orders={orders} />
    </div>
  );
}
