import BestReviewTitle from './BestReviewTitle';
import BestReviewContents from './BestReviewContents';
import type {
  BestReviewType,
  ItemContentType,
} from 'Pages/CYW/ReviewManagement';

interface OwnProps {
  bestReview: BestReviewType[];
  item: ItemContentType[];
  setBestReview: React.Dispatch<React.SetStateAction<BestReviewType[]>>;
}

export default function BestReviewManagement({
  bestReview,
  item,
  setBestReview,
}: OwnProps) {
  return (
    <div className="flex flex-col border-gray-400 border border-r-0 border-b-0 rounded-t-lg w-300">
      <BestReviewTitle />
      <BestReviewContents
        bestReview={bestReview}
        item={item}
        setBestReview={setBestReview}
      />
    </div>
  );
}
