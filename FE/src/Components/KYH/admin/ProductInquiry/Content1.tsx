export default function Content1() {
  return (
    <div className="flex items-center border-b border-b-gray-400 h-1/3">
      <label className="flex items-center">
        <input type="checkbox" className="w-6 h-6 ml-6" />
        <span className="ml-2 text-lg text-gray-500">문의중</span>
      </label>
      <label className="flex items-center">
        <input type="checkbox" className="w-6 h-6 ml-6" />
        <span className="ml-2 text-lg text-gray-500">답변완료</span>
      </label>
    </div>
  );
}
