import type {
  BestReviewType,
  ItemContentType,
} from 'Pages/CYW/ReviewManagement';
import api from 'api';

interface OwnProps {
  bestReview: BestReviewType[];
  item: ItemContentType[];
  setBestReview: React.Dispatch<React.SetStateAction<BestReviewType[]>>;
}

export default function BestReviewContents({
  bestReview,
  item,
  setBestReview,
}: OwnProps) {
  const commonStyle =
    'flex items-center justify-center text-gray-700 border-b border-r border-b-gray-400 border-r-gray-400';
  const REVIEW_API_URL = 'api/reviews';

  const bestReviewUnregister = (reviewItem: BestReviewType, i: number) => {
    const requestBody = { reviewIdList: [reviewItem.reviewId] };

    api
      .delete(`${REVIEW_API_URL}/best`, { data: requestBody })
      .then((deleteRes) => {
        const copiedBestReview = [...bestReview];
        copiedBestReview.splice(i, 1);
        setBestReview(copiedBestReview);
      })
      .catch((error) => {
        console.log('Error:', error);
      });
  };

  return (
    <div className="flex flex-col">
      {bestReview.map((reviewItem, i) => {
        const matchingItem = item.find((el) => el.itemId === reviewItem.itemId);

        return (
          <div className="flex" key={i}>
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
              <button
                className="bg-slate-200 border-gray-400 border px-4 rounded-md py-2"
                onClick={() => {
                  bestReviewUnregister(reviewItem, i);
                }}
              >
                해제
              </button>
            </span>
          </div>
        );
      })}
    </div>
  );
}
