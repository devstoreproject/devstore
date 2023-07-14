export default function RegisteredReviewContents({
  commonStyle,
}: {
  commonStyle: string;
}) {
  return (
    <div className="flex w-full h-16 border-b border-b-gray-400">
      <span className={`${commonStyle} w-16`}>1</span>
      <span className={`${commonStyle} w-56`}>
        웨이코스 싱크웨이 토체프 D&T 콜라보 체리 키보드
      </span>
      <span className={`${commonStyle} w-175`}>
        써보니까 너무 너무 좋네용 써보니까 너무 너무 좋네용 써보니까 너무 너무
        좋네용 써보니까 너무 너무 좋네용 써보니까 너무 너무 좋네용 써보니까 너무
        너무 좋네용
      </span>
      <span className={`${commonStyle} w-25`}>5</span>
      <button className="flex items-center justify-center border-box border-b w-32 bg-gray-300 rounded-lg">
        등록
      </button>
    </div>
  );
}
