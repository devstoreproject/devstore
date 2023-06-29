export default function AddressContainer() {
  return (
    <>
      <div className="flex items-center mb-4">
        <span className="w-32 text-gray-500">주소</span>
        <input
          type="text"
          className="h-10 pl-4 mr-4 text-sm border border-gray-300 w-72 rounded-3xl"
        />
        <button className="w-20 h-10 text-sm text-white bg-black rounded-3xl">
          주소검색
        </button>
      </div>
      <div className="flex items-center mb-4">
        <input
          type="text"
          className="h-10 pl-4 ml-32 mr-4 text-sm border border-gray-300 w-80 rounded-3xl"
        />
        <input
          type="text"
          className="h-10 pl-4 mr-4 text-sm border border-gray-300 w-60 rounded-3xl"
          placeholder="상세주소를 입력해 주세요"
        />
      </div>
    </>
  );
}
