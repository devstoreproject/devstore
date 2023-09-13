export default function AllReviewTitle() {
  return (
    <div className="flex w-full">
      <span className="flex items-center justify-center text-gray-600 bg-white border-b border-r rounded-tl-lg border-b-gray-400 border-r-gray-400 w-16 py-4"></span>
      <span className="flex items-center justify-center text-gray-600 bg-slate-100 border-b border-r border-b-gray-400 border-r-gray-400 w-56 py-4">
        제품명
      </span>
      <span className="flex items-center justify-center text-gray-600 bg-slate-100 border-b border-r border-b-gray-400 border-r-gray-400 w-200 py-4">
        리뷰
      </span>
      <span className="flex items-center justify-center text-gray-600 bg-slate-100 border-b border-r rounded-tr-lg border-b-gray-400 border-r-gray-400 w-32 py-4">
        등록하기
      </span>
    </div>
  );
}
