export default function TableTitle() {
  return (
    <div className="flex items-center justify-between h-12 px-6 bg-white border-b rounded-t-lg border-b-gray-400">
      <div className="w-5"></div>
      <div className="text-white">1</div>
      <span className="text-center text-gray-600 w-120">상품명</span>
      <span className="w-16 text-center text-gray-600">판매량</span>
      <span className="w-20 text-center text-gray-600">매출액</span>
      <span className="w-16 text-center text-gray-600">판매비율</span>
    </div>
  );
}
