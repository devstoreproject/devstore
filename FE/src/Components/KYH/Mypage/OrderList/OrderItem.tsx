export default function OrderItem() {
  return (
    <li className="flex items-center p-4 mb-2 bg-gray-100 border border-gray-400 rounded-lg">
      <img className="w-32 h-24 mr-4 bg-gray-300 rounded-lg" />
      <div className="flex flex-col">
        <span className="text-sm">
          알파스캔 AOC Q32V3S QHD IPS 75 시력보호 무결점 외 3개
        </span>
        <span className="flex mb-2 text-sm text-gray-500">2023.06.08</span>
        <span className="text-sm font-bold">주문수량 : 4</span>
      </div>
    </li>
  );
}
