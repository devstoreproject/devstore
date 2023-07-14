import ReviewElement from './ReviewElement';
import ReviewPagination from '../../SharedComponent/Pagination';

export default function ReviewTab({ tab }: { tab: number }) {
  if (tab === 1) {
    return (
      <div className="bg-slate-100 rounded-lg">
        <p className="py-4 pl-10">상품 리뷰</p>
        <ReviewElement />
        <ReviewElement />
        <ReviewElement />
        <ReviewElement />
        <ReviewPagination />
      </div>
    );
  }
}
