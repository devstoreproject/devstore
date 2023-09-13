import ReviewElement from './ReviewElement';
import ReviewRegister from './ReviewRegister';
import type { ReviewContentType } from '../Tab';
import PaginationContainer from '../PaginationContainer';

interface OwnProps {
  tab: number;
  review: ReviewContentType[] | null;
  page: number;
  totalPage: number;
  setPage: React.Dispatch<React.SetStateAction<number>>;
  setReview: React.Dispatch<React.SetStateAction<ReviewContentType[] | null>>;
}

export default function ReviewTab({
  tab,
  review,
  setReview,
  page,
  setPage,
  totalPage,
}: OwnProps) {
  return tab === 1 ? (
    <div className="bg-slate-100 rounded-lg">
      <p className="border-b-2 py-4 pl-8">상품 리뷰</p>
      <ReviewElement review={review} setReview={setReview} />
      {totalPage === 0 ? null : (
        <PaginationContainer
          page={page}
          setPage={setPage}
          totalPage={totalPage}
        />
      )}
      <ReviewRegister review={review} setReview={setReview} />
    </div>
  ) : null;
}
