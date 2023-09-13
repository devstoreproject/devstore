export default function OrderItemDetailTitle() {
  return (
    <div className="flex items-center h-8 border-b border-gray-400">
      <span className="w-4 ml-2 text-sm text-center text-white">1</span>
      <span className="w-64 mx-2 text-sm font-bold text-center">제품명</span>
      <span className="text-sm font-bold text-center w-52">가격</span>
      <span className="text-sm font-bold text-center w-60">리뷰</span>
    </div>
  );
}
