export default function OrderListTableContents() {
  return (
    <div className="flex mb-2">
      <span className="mr-6">1</span>
      <span className="w-48 text-center">2023060415040000017</span>
      <span className="text-center w-28">배송중</span>
      <span className="w-40 text-center">2023-06-04 15:04:10</span>
      <p className="w-56 ml-4 text-center truncate">
        웨이코스 씽크웨이 토체프 D&Tsdfsdfds
      </p>
      <span className="w-24 ml-4 text-center">블랙</span>
      <span className="text-center w-28">155,000</span>
      {/* <span className="text-center w-60">111-111-1111-1111</span> */}
      <div className="flex justify-center w-60">
        <input
          type="text"
          className="pl-2 mr-2 text-sm border border-gray-400 rounded-md w-44"
          placeholder="번호를 입력해주세요(- 제외)"
        />
        <button className="px-2 bg-gray-200 border border-gray-400 rounded-lg">
          확인
        </button>
      </div>
    </div>
  );
}