export default function Content1() {
  return (
    <div className="flex items-center border-b border-b-gray-400 h-1/4">
      <label className="flex items-center">
        <input type="checkbox" className="w-6 h-6 ml-6" />
        <span className="ml-2 text-lg text-gray-500">입금 예정</span>
      </label>
      <label className="flex items-center">
        <input type="checkbox" className="w-6 h-6 ml-6" />
        <span className="ml-2 text-lg text-gray-500">입금 확인</span>
      </label>
      <label className="flex items-center">
        <input type="checkbox" className="w-6 h-6 ml-6" />
        <span className="ml-2 text-lg text-gray-500">배송 준비 중</span>
      </label>
      <label className="flex items-center">
        <input type="checkbox" className="w-6 h-6 ml-6" />
        <span className="ml-2 text-lg text-gray-500">배송 중</span>
      </label>
      <label className="flex items-center">
        <input type="checkbox" className="w-6 h-6 ml-6" />
        <span className="ml-2 text-lg text-gray-500">배송 완료</span>
      </label>
      <label className="flex items-center">
        <input type="checkbox" className="w-6 h-6 ml-6" />
        <span className="ml-2 text-lg text-gray-500">구매 확정</span>
      </label>
    </div>
  );
}
