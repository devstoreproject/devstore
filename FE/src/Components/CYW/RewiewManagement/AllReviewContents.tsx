import type {
  BestReviewType,
  ItemContentType,
  ReviewContentType,
} from 'Pages/CYW/ReviewManagement';
import api from 'api';

interface OwnProps {
  review: ReviewContentType[];
  bestReview: BestReviewType[];
  item: ItemContentType[];
  setBestReview: React.Dispatch<React.SetStateAction<BestReviewType[]>>;
}

export default function AllReviewContents({
  review,
  bestReview,
  item,
  setBestReview,
}: OwnProps) {
  const commonStyle =
    'flex items-center justify-center text-gray-700 border-b border-r border-b-gray-400 border-r-gray-400';
  const REVIEW_API_URL = 'api/reviews';

  const bestReviewRegister = (reviewItem: ReviewContentType, i: number) => {
    const requestBody = {
      reviewIdList: [reviewItem.reviewId],
    };
    api
      .post(`${REVIEW_API_URL}/best`, requestBody)
      .then((res) => {
        const copiedBestReview = [...bestReview];
        setBestReview([
          ...copiedBestReview,
          {
            best: true,
            comment: reviewItem.comment,
            createdAt: reviewItem.createdAt,
            image: reviewItem.image,
            itemId: reviewItem.itemId,
            modifiedAt: reviewItem.modifiedAt,
            reviewId: reviewItem.reviewId,
            userId: reviewItem.userId,
            userName: reviewItem.userName,
          },
        ]);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return (
    <div className="flex flex-col">
      {review.map((reviewItem, i) => {
        const matchingItem = item.find((el) => el.itemId === reviewItem.itemId);

        return (
          <div key={i} className="flex w-full">
            <span className={`${commonStyle} w-16 bg-slate-100 py-8`}>
              {i + 1}
            </span>
            <span className={`${commonStyle} border-r-gray-400 w-56 bg-white`}>
              {/* eslint-disable-next-line @typescript-eslint/strict-boolean-expressions */}
              {matchingItem ? matchingItem.name : ''}
            </span>
            <span className={`${commonStyle} border-r-gray-400 w-200 bg-white`}>
              {reviewItem.comment}
            </span>
            <span className={`${commonStyle} border-r-gray-400 w-32 bg-white`}>
              {bestReview.findIndex(
                (bestItem) => bestItem.reviewId === reviewItem.reviewId
              ) === -1 ? (
                <button
                  className="bg-slate-200 border-gray-400 border px-4 rounded-md py-2"
                  onClick={() => {
                    bestReviewRegister(reviewItem, i);
                  }}
                >
                  등록
                </button>
              ) : null}
            </span>
          </div>
        );
      })}
    </div>
  );
}
