export default function TableTitle() {
  return (
    <div className="flex items-center w-full h-12 bg-white border-b rounded-t-lg border-b-gray-400">
      <span className="text-gray-600 ml-28 w-104">상품명</span>
      <span className="ml-10 text-gray-600">총 판매량</span>
      <span className="ml-16 text-gray-600">주문량</span>
      <span className="ml-16 mr-6 text-gray-600">재고</span>
      <span className="ml-16 text-gray-600">금액</span>
    </div>
  );
}
