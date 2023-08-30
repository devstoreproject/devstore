export default function OrderListTableTitle() {
  return (
    <div className="flex justify-between mb-4 text-gray-500">
      <span className="text-white">1</span>
      <span className="w-48 text-center">주문번호</span>
      <span className="w-20 text-center">처리상태</span>
      <span className="w-40 text-center">주문일자</span>
      <span className="text-center w-60">주문제품</span>
      <span className="w-40 text-center">총 가격</span>
    </div>
  );
}
