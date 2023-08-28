export default function ProductDetailTitle() {
  return (
    <div className="flex flex-col w-40">
      <div className="flex items-center justify-center h-10 bg-gray-200 border border-gray-300 rounded-tl-lg">
        상품명
      </div>
      <div className="flex items-center justify-center h-10 bg-gray-200 border border-gray-300">
        카테고리
      </div>
      <div className="flex items-center justify-center h-10 bg-gray-200 border border-gray-300">
        가격
      </div>
      <div className="flex items-center justify-center h-10 bg-gray-200 border border-gray-300">
        총 수량
      </div>
      <div className="flex items-center justify-center h-10 bg-gray-200 border border-gray-300">
        판매량
      </div>
      <div className="flex items-center justify-center h-48 bg-gray-200 border border-gray-300 rounded-bl-lg">
        옵션
      </div>
    </div>
  );
}
