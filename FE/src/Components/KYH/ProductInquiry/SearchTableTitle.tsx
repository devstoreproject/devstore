export default function SearchTableTitle() {
  return (
    <div className="flex flex-col w-32 h-full">
      <span className="flex items-center justify-center text-gray-600 bg-white border-b border-r rounded-tl-lg h-1/3 border-b-gray-400 border-r-gray-400">
        처리상태
      </span>
      <span className="flex items-center justify-center text-gray-600 bg-white border-b border-r h-1/3 border-b-gray-400 border-r-gray-400">
        문의일자
      </span>
      <span className="flex items-center justify-center text-gray-600 bg-white border-r rounded-bl-lg h-1/3 border-r-gray-400">
        검색
      </span>
    </div>
  );
}
