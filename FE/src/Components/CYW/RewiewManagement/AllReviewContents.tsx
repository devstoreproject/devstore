export default function AllReviewContents({
  commonStyle,
}: {
  commonStyle: string;
}) {
  return (
    <div className="flex w-full h-16 border-b border-b-gray-400 text-center">
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
      <div className="flex items-center justify-center border-box bg-gray-300 rounded-lg w-32">
        <button>해제</button>
      </div>
    </div>
  );
}
