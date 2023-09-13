import type {
  BestReviewType,
  ItemContentType,
  ReviewContentType,
} from 'Pages/CYW/ReviewManagement';
import AllReviewContents from './AllReviewContents';
import AllReviewTitle from './AllReviewTitle';

interface OwnProps {
  review: ReviewContentType[];
  bestReview: BestReviewType[];
  item: ItemContentType[];
  setBestReview: React.Dispatch<React.SetStateAction<BestReviewType[]>>;
}

export default function AllReviewManagement({
  review,
  item,
  bestReview,
  setBestReview,
}: OwnProps) {
  return (
    <div className="flex flex-col border-gray-400 border border-r-0 border-b-0 rounded-t-lg w-300">
      <AllReviewTitle />
      <AllReviewContents
        review={review}
        bestReview={bestReview}
        item={item}
        setBestReview={setBestReview}
      />
    </div>
  );
}
