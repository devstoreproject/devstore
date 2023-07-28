export default function OrderListTableTitle() {
  return (
    <div className="flex justify-between mb-4 text-gray-500">
      <span className="text-white">1</span>
      <span className="w-48 text-center">주문번호</span>
      <span className="w-20 text-center">처리상태</span>
      <span className="w-40 text-center">주문일자</span>
      <span className="text-center w-52">제품명</span>
      <span className="w-20 text-center">옵션명</span>
      <span className="w-24 text-center">제품가격</span>
      <span className="w-56 text-center">운송장 번호</span>
    </div>
  );
}
