import { useParams } from 'react-router-dom';
import type { ReviewContentType } from '../Tab';
import api from 'api';
// import { useState } from 'react';

export interface OwnProps {
  review: ReviewContentType;
  setReview: React.Dispatch<React.SetStateAction<ReviewContentType[] | null>>;
  i: number;
}

export default function UserReview({ review, setReview, i }: OwnProps) {
  // const [edit, setEdit] = useState<number | null>(null);
  const { id } = useParams();
  const userId: string | null = localStorage.getItem('userId');
  const parsedUserId: number | null = userId !== null ? Number(userId) : null;
  const isCurrentUserAuthor =
    parsedUserId !== null && parsedUserId === review.userId;

  const deleteHandler = () => {
    const confirmDelete = window.confirm('정말로 삭제하시겠습니까?');
    const deleteApiUrl = `api/items/${id as string}/reviews/${review.reviewId}`;

    if (confirmDelete) {
      api
        .delete(deleteApiUrl, {
          headers: {
            Authorization: localStorage.getItem('authorization'),
          },
        })
        .then((res) => {
          setReview((prevReview) => {
            if (prevReview === null) {
              return [];
            }
            return prevReview.filter((item, index) => index !== i);
          });
        })
        .catch((err) => {
          console.log(err);
        });
    }
  };

  return (
    <div className="flex">
      {/* {
        edit === i ? (
          <input
            type='text'
            value={review.comment}
            onChange={(e) => {
              const editedReview = [];
            }}
        )
      } */}
      <p className="pl-8">{review.comment}</p>
      <div className="absolute right-96">
        {isCurrentUserAuthor ? (
          <>
            <button className="border border-black rounded-lg px-2">
              수정
            </button>
            <button
              className="border border-black rounded-lg px-2"
              onClick={deleteHandler}
            >
              삭제
            </button>
          </>
        ) : null}
      </div>
    </div>
  );
}
