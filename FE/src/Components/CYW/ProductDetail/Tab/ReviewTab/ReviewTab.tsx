import ReviewElement from './ReviewElement';
import PaginationContainer from './PaginationContainer';
import ReviewRegister from './ReviewRegister';
import type { ReviewContentType } from '../Tab';

interface OwnProps {
  tab: number;
  review: ReviewContentType[] | null;
}

export default function ReviewTab({ tab, review }: OwnProps) {
  return tab === 1 ? (
    <div className="bg-slate-100 rounded-lg">
      <p className="border-b-2 py-4 pl-8">상품 리뷰</p>
      <ReviewElement review={review} />
      <PaginationContainer />
      <ReviewRegister />
    </div>
  ) : null;
}
