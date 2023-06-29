import { BiSearch } from 'react-icons/bi';

export default function Content4() {
  return (
    <div className="flex items-center h-1/4">
      <select className="w-32 h-10 ml-4 text-lg text-center text-gray-500 bg-white border border-gray-400 rounded-3xl">
        <option value="">주문자명</option>
        <option value="">상품명</option>
      </select>
      <div className="flex items-center pl-2 ml-2 text-2xl text-gray-500 bg-white border border-gray-400 w-80 rounded-3xl">
        <BiSearch />
        <input
          type="text"
          placeholder="내용을 입력해 주세요"
          className="w-full h-10 ml-2 text-lg text-gray-500 rounded-r-3xl focus:outline-none"
        />
      </div>
    </div>
  );
}
