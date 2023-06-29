import Review from './Review';

export default function Reviews() {
  return (
    <div className="flex flex-col">
      <span className="mb-4 font-bold">리뷰 목록</span>
      <ul>
        <Review />
        <Review />
        <Review />
      </ul>
    </div>
  );
}
