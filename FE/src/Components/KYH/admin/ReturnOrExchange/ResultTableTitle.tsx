export default function ResultTableTitle() {
  return (
    <div className="flex w-full h-12 bg-white border-b rounded-t-lg border-b-gray-400">
      <span className="flex items-center justify-center w-20">요청</span>
      <span className="flex items-center justify-center w-20">주문자명</span>
      <span className="flex items-center justify-center w-36">주문번호</span>
      <span className="flex items-center justify-center w-56">사유</span>
      <span className="flex items-center justify-center w-32">신청일자</span>
      <span className="flex items-center justify-center w-40">운송장번호</span>
      <span className="flex items-center justify-center w-32">배송현황</span>
      <span className="flex items-center justify-center w-32">환불액</span>
      <span className="flex items-center justify-center w-24">차감액</span>
    </div>
  );
}
