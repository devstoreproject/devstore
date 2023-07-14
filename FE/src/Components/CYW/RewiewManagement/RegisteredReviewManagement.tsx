import RegisteredReviewTitle from './RegisteredReviewTitle';
import RegisteredReviewContents from './RegisteredReviewContents';

export default function RegisteredReviewManagement() {
  const commonStyle =
    'flex items-center justify-center border-box text-center border-r-2';
  return (
    <div className="flex flex-col bg-gray-100 border-gray-400 border rounded-lg h-120 w-300">
      <RegisteredReviewTitle commonStyle={commonStyle} />
      <RegisteredReviewContents commonStyle={commonStyle} />
    </div>
  );
}
