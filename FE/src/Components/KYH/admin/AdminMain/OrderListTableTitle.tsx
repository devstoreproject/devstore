export default function OrderListTableTitle() {
  return (
    <div className="flex mb-4 text-gray-500">
      <span className="w-48 ml-8 text-center">주문번호</span>
      <span className="text-center w-28">처리상태</span>
      <span className="w-40 text-center">주문일자</span>
      <span className="w-56 ml-4 text-center">제품명</span>
      <span className="w-24 ml-4 text-center">옵션명</span>
      <span className="text-center w-28">제품가격</span>
      <span className="text-center w-60">운송장 번호</span>
    </div>
  );
}
