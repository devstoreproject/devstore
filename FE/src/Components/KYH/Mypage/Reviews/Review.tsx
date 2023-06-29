export default function Review() {
  return (
    <li className="flex pb-4 mb-4 border-b border-b-gray-300">
      <img className="w-32 mr-10 bg-gray-300 h-28 rounded-xl" />
      <div className="flex flex-col w-104">
        <p className="mt-2 text-sm text-gray-500">
          알파스캔 AOC Q32V3S QHD IPS 75 시력보호 무결점 외 3개
        </p>
        <span className="text-sm text-gray-500">116,000원 | 4개</span>
        {/* <p className="mt-4 text-sm">사용해보니 좋습니다!</p> */}
        <button className="h-10 pl-4 mt-4 text-sm text-left text-gray-500 bg-white border border-gray-300 rounded-3xl">
          리뷰 작성하러 가기 {'>'}
        </button>
      </div>
      <span className="mt-2 ml-4 text-sm text-gray-500">2023.06.08 배송</span>
    </li>
  );
}
