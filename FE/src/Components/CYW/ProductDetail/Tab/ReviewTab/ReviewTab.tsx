import ReviewElement from './ReviewElement';
import PaginationContainer from './PaginationContainer';

export default function ReviewTab({ tab }: { tab: number }) {
  return tab === 1 ? (
    <div className="bg-slate-100 rounded-lg">
      <p className="border-b-2 py-4 pl-8">상품 리뷰</p>
      <ReviewElement />
      <ReviewElement />
      <ReviewElement />
      <ReviewElement />
      <PaginationContainer />
    </div>
  ) : null;
}
