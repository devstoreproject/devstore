export default function Content5() {
  return (
    <div className="flex items-center border-b border-b-gray-400 h-1/6">
      <input
        type="text"
        placeholder="2023.06.14"
        className="h-10 ml-4 text-lg text-center text-gray-500 bg-white border border-gray-400 w-52 rounded-3xl"
      />
      <span className="ml-4 text-3xl text-gray-500">~</span>
      <input
        type="text"
        placeholder="2023.07.13"
        className="h-10 ml-4 text-lg text-center text-gray-500 bg-white border border-gray-400 w-52 rounded-3xl"
      />
      <button className="w-20 h-10 ml-4 border border-gray-400 rounded-3xl">
        오늘
      </button>
      <button className="w-20 h-10 ml-4 border border-gray-400 rounded-3xl">
        1주일
      </button>
      <button className="w-20 h-10 ml-4 border border-gray-400 rounded-3xl">
        3개월
      </button>
      <button className="w-20 h-10 ml-4 border border-gray-400 rounded-3xl">
        6개월
      </button>
    </div>
  );
}
