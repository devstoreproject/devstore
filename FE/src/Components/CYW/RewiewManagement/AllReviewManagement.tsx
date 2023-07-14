import AllReviewContents from './AllReviewContents';
import AllReviewTitle from './AllReviewTitle';

export default function AllReviewManagement() {
  const commonStyle = 'flex items-center justify-center border-box border-r-2';
  return (
    <div className="flex flex-col bg-gray-100 border-gray-400 border rounded-lg h-120 w-300">
      <AllReviewTitle commonStyle={commonStyle} />
      <AllReviewContents commonStyle={commonStyle} />
    </div>
  );
}
