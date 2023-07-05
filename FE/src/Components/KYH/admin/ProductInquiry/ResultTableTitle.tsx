export default function ResultTableTitle() {
  return (
    <div className="flex h-12 pl-10 bg-white border-b rounded-t-lg border-b-gray-400">
      <span className="flex items-center justify-center w-96">상품명</span>
      <span className="flex items-center justify-center w-128">사유</span>
      <span className="flex items-center justify-center w-36">문의일자</span>
      <span className="flex items-center justify-center w-36">처리상태</span>
    </div>
  );
}
