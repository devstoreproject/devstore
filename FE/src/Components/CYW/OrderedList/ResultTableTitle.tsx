export default function ResultTableTitle({
  commonStyle,
}: {
  commonStyle: string;
}) {
  return (
    <div className="flex w-full h-12 bg-white border-b rounded-t-lg border-b-gray-400">
      <span className={`${commonStyle} w-16`}></span>
      <span className={`${commonStyle} w-56`}>주문 번호</span>
      <span className={`${commonStyle} w-24`}>처리 상태</span>
      <span className={`${commonStyle} w-52`}>주문 일자</span>
      <span className={`${commonStyle} w-60`}>제품명</span>
      <span className={`${commonStyle} w-24`}>옵션명</span>
      <span className={`${commonStyle} w-32`}>제품 가격</span>
      <span className="flex items-center justify-center border-box w-60">
        운송장 번호
      </span>
    </div>
  );
}
