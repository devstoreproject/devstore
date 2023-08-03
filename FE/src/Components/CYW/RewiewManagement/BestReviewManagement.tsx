import BestReviewTitle from './BestReviewTitle';
import BestReviewContents from './BestReviewContents';

export default function BestReviewManagement() {
  return (
    <div className="flex flex-col bg-gray-100 border border-gray-400 rounded-lg h-100 w-300">
      <BestReviewTitle />
      <BestReviewContents />
    </div>
  );
}
