import type { ReviewContentType } from '../Tab';
import { useState } from 'react';
import ReviewEditDeleteBtn from './ReviewEditDeleteBtn';

export interface OwnProps {
  review: ReviewContentType[];
  reviewItem: ReviewContentType;
  setReview: React.Dispatch<React.SetStateAction<ReviewContentType[] | null>>;
  i: number;
}

export default function UserReview({
  review,
  reviewItem,
  setReview,
  i,
}: OwnProps) {
  const [edit, setEdit] = useState<number | null>(null);
  return (
    <div className="flex">
      {edit === i ? (
        <div className="pl-8 w-200">
          <input
            className="w-full"
            type="text"
            value={reviewItem.comment}
            onChange={(e) => {
              const editedReview = [...review];
              editedReview[i].comment = e.target.value;
              setReview(editedReview);
            }}
          />
        </div>
      ) : (
        <p className="pl-8">{reviewItem.comment}</p>
      )}
      <ReviewEditDeleteBtn
        i={i}
        edit={edit}
        setEdit={setEdit}
        review={review}
        reviewItem={reviewItem}
        setReview={setReview}
      />
    </div>
  );
}
