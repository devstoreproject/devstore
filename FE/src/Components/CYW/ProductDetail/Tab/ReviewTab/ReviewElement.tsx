import UserInfo from './UserInfo';
import UserReview from './UserReview';
import type { ReviewContentType } from '../Tab';

interface OwnProps {
  review: ReviewContentType[] | null;
}

export default function ReviewElement({ review }: OwnProps) {
  return (
    <div>
      {review?.map((reviewItem) => (
        <div
          key={reviewItem.reviewId}
          className="flex items-center border-b-2 py-4 pl-8 px-10"
        >
          <UserInfo review={reviewItem} />
          <UserReview review={reviewItem} />
        </div>
      ))}
    </div>
  );
}
