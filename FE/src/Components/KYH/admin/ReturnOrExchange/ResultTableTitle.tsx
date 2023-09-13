export default function ResultTableTitle() {
  return (
    <div className="flex items-center justify-between h-12 px-6 bg-white border-b rounded-t-lg border-b-gray-400">
      <span className="text-white">1</span>
      <span className="w-12 text-center">요청</span>
      <span className="w-16 text-center">주문자명</span>
      <span className="text-center w-36">주문번호</span>
      <span className="w-48 text-center">사유</span>
      <span className="w-32 text-center">신청일자</span>
      <span className="w-40 text-center">운송장번호</span>
      <span className="w-20 text-center">배송현황</span>
      <span className="w-24 text-center">환불액</span>
      <span className="w-16 text-center">차감액</span>
    </div>
  );
}
