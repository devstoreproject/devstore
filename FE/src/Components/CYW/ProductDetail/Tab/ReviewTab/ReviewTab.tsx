import ReviewElement from './ReviewElement';
import ReviewPagination from '../../SharedComponent/Pagination';

export default function ReviewTab({ tab }: { tab: number }) {
  return tab === 1 ? (
    <div className="rounded-lg bg-slate-100">
      <p className="py-4 pl-10">상품 리뷰</p>
      <ReviewElement />
      <ReviewElement />
      <ReviewElement />
      <ReviewElement />
      <ReviewPagination />
    </div>
  ) : null;
}
