import type { ReviewContentType } from '../Tab';

export interface OwnProps {
  review: ReviewContentType;
}

export default function UserReview({ review }: OwnProps) {
  return <p className="w-400 pl-8">{review.comment}</p>;
}
