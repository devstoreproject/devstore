export default function TableTitle() {
  return (
    <div className="flex items-center justify-between h-12 px-6 bg-white border-b rounded-t-lg border-b-gray-400">
      <div className="w-10 text-white" />
      <span className="mr-4 text-center text-gray-600 w-96">상품명</span>
      <span className="w-16 text-center text-gray-600">판매량</span>
      <span className="w-40 text-center text-gray-600">매출액</span>
      <span className="w-16 text-center text-gray-600">판매비율</span>
    </div>
  );
}
