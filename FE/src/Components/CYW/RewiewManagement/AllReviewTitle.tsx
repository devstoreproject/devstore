export default function AllReviewTitle({
  commonStyle,
}: {
  commonStyle: string;
}) {
  return (
    <div className="flex w-full h-12 bg-white border border-b rounded-t-lg border-b-gray-400">
      <span className={`${commonStyle} w-16`}></span>
      <span className={`${commonStyle} w-56`}>제품 명</span>
      <span className={`${commonStyle} w-175`}>리뷰 내용</span>
      <span className={`${commonStyle} w-25`}>평점</span>
      <span className="flex items-center justify-center border-box border-b w-32">
        등록하기
      </span>
    </div>
  );
}
