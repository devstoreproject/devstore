import AllReviewContents from './AllReviewContents';
import AllReviewTitle from './AllReviewTitle';

export default function AllReviewManagement() {
  return (
    <div className="flex flex-col bg-gray-100 border-gray-400 border rounded-lg h-120 w-300">
      <AllReviewTitle />
      <AllReviewContents />
    </div>
  );
}
