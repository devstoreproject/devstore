export default function Content2() {
  return (
    <div className="flex items-center border-b border-b-gray-400 h-1/4">
      <label className="flex items-center">
        <input type="checkbox" className="w-6 h-6 ml-6" />
        <span className="ml-2 text-lg text-gray-500">교환</span>
      </label>
      <label className="flex items-center">
        <input type="checkbox" className="w-6 h-6 ml-6" />
        <span className="ml-2 text-lg text-gray-500">교환요청</span>
      </label>
      <label className="flex items-center">
        <input type="checkbox" className="w-6 h-6 ml-6" />
        <span className="ml-2 text-lg text-gray-500">교환승인</span>
      </label>
      <label className="flex items-center">
        <input type="checkbox" className="w-6 h-6 ml-6" />
        <span className="ml-2 text-lg text-gray-500">교환회수완료</span>
      </label>
      <label className="flex items-center">
        <input type="checkbox" className="w-6 h-6 ml-6" />
        <span className="ml-2 text-lg text-gray-500">교환배송중</span>
      </label>
      <label className="flex items-center">
        <input type="checkbox" className="w-6 h-6 ml-6" />
        <span className="ml-2 text-lg text-gray-500">교환확정</span>
      </label>
    </div>
  );
}
