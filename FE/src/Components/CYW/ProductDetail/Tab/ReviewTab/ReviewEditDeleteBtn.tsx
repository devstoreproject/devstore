import api from 'api';
import type { ReviewContentType } from '../Tab';
import { useParams } from 'react-router-dom';

export interface OwnProps {
  i: number;
  edit: number | null;
  setEdit: React.Dispatch<React.SetStateAction<number | null>>;
  review: ReviewContentType[];
  reviewItem: ReviewContentType;
  setReview: React.Dispatch<React.SetStateAction<ReviewContentType[] | null>>;
}

export default function ReviewEditDeleteBtn({
  edit,
  reviewItem,
  review,
  setReview,
  setEdit,
  i,
}: OwnProps) {
  const { id } = useParams();
  const userId: string | null = localStorage.getItem('userId');
  const parsedUserId: number | null = userId !== null ? Number(userId) : null;
  const isCurrentUserAuthor =
    parsedUserId !== null && parsedUserId === reviewItem.userId;

  const deleteHandler = () => {
    const confirmDelete = window.confirm('정말로 삭제하시겠습니까?');
    const deleteApiUrl = `api/items/${id as string}/reviews/${
      reviewItem.reviewId
    }`;

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

  const editHandler = (i: number) => {
    if (edit === i && review !== null) {
      const editedReview = [...review];
      const comment = editedReview[i].comment;
      api
        .patch(
          `api/items/${id as string}/reviews/${reviewItem.reviewId}`,
          { comment },
          {
            headers: { Authorization: localStorage.getItem('authorization') },
          }
        )
        .then(() => {
          setEdit(null);
        })
        .catch((err) => {
          console.log(err);
        });
    } else {
      setEdit(i);
    }
  };

  return (
    <div className="absolute right-96">
      {isCurrentUserAuthor ? (
        <>
          <button
            className="border border-black rounded-lg px-2"
            onClick={() => {
              editHandler(i);
            }}
          >
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
  );
}
