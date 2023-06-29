import OrderItem from './OrderItem';

export default function OrderHistory() {
  return (
    <div className="flex flex-col mt-14">
      <span className="mb-4 font-bold">주문 내역</span>
      <ul className="w-full p-2 bg-white border border-gray-300 rounded-tl-lg rounded-tr-lg">
        <OrderItem />
        <OrderItem />
      </ul>
      <div className="flex items-center justify-center h-12 bg-gray-100 border-b rounded-bl-lg rounded-br-lg border-x border-b-gray-300 border-x-gray-300">
        <span className="text-sm">+ 주문 내역 더 보기</span>
      </div>
    </div>
  );
}
