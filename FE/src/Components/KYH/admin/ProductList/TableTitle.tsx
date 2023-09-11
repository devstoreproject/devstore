export default function TableTitle() {
  return (
    <div className="flex items-center justify-between h-12 px-6 font-bold bg-white border-b rounded-t-lg border-b-gray-400">
      <div className="w-5"></div>
      <div className="w-5"></div>
      <span className="text-center text-gray-600 w-100">상품명</span>
      <span className="w-20 text-center text-gray-600">총 수량</span>
      <span className="w-16 text-center text-gray-600">판매량</span>
      <span className="w-20 text-center text-gray-600">재고</span>
      <span className="text-center text-gray-600 w-28">판매금액</span>
    </div>
  );
}
