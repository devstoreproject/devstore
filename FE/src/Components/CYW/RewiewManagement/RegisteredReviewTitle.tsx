export default function RegisteredReviewTitle({
  commonStyle,
}: {
  commonStyle: string;
}) {
  return (
    <div className="flex w-full h-12 bg-white border rounded-t-lg border-b-gray-400">
      <span className={`${commonStyle} w-16 rounded-tl-lg`}></span>
      <span className={`${commonStyle} w-56`}>제품 명</span>
      <span className={`${commonStyle} w-175 border-gray-100`}>리뷰 내용</span>
      <span className={`${commonStyle} w-25`}>평점</span>
      <span className="flex items-center justify-center w-32">해제하기</span>
    </div>
  );
}
