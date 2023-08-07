export default function ResultTableContents({
  commonStyle,
}: {
  commonStyle: string;
}) {
  return (
    <div className="flex w-full h-12 border-b border-b-gray-400">
      <span className={`${commonStyle} w-16`}>1</span>
      <span className={`${commonStyle} w-56`}>2023060415040000001</span>
      <span className={`${commonStyle} w-24`}>배송 중</span>
      <span className={`${commonStyle} w-52`}>2023-06-04 15:04:10</span>
      <span className={`${commonStyle} w-60`}>
        웨이코스 씽크웨이 토체프 D&T...
      </span>
      <span className={`${commonStyle} w-24`}>블랙</span>
      <span className={`${commonStyle} w-32`}>350,000</span>
      <span className="flex items-center justify-center border-box w-60">
        111-111-1111-1111
      </span>
    </div>
  );
}
