export default function Content1() {
  return (
    <div className="flex items-center border-b border-b-gray-400 h-1/4">
      <label className="flex items-center">
        <input type="checkbox" className="w-6 h-6 ml-6" />
        <span className="ml-2 text-lg text-gray-500">반품</span>
      </label>
      <label className="flex items-center">
        <input type="checkbox" className="w-6 h-6 ml-6" />
        <span className="ml-2 text-lg text-gray-500">반품요청</span>
      </label>
      <label className="flex items-center">
        <input type="checkbox" className="w-6 h-6 ml-6" />
        <span className="ml-2 text-lg text-gray-500">반품승인</span>
      </label>
      <label className="flex items-center">
        <input type="checkbox" className="w-6 h-6 ml-6" />
        <span className="ml-2 text-lg text-gray-500">반품회수완료</span>
      </label>
      <label className="flex items-center">
        <input type="checkbox" className="w-6 h-6 ml-6" />
        <span className="ml-2 text-lg text-gray-500">반품확정</span>
      </label>
    </div>
  );
}
