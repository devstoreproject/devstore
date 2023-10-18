export default function ResultTableTitle({
  commonStyle,
}: {
  commonStyle: string;
}) {
  return (
    <div className="flex w-full h-12 font-bold bg-white border-b rounded-t-lg border-b-gray-400">
      <span className={`${commonStyle} w-16`}></span>
      <span className={`${commonStyle} w-72`}>주문 번호</span>
      <span className={`${commonStyle} w-40`}>처리 상태</span>
      <span className={`${commonStyle} w-52`}>주문 일자</span>
      <span className={`${commonStyle} w-80`}>주문 제품</span>
      <span className={`${commonStyle} w-40`}>주문 가격</span>
    </div>
  );
}
