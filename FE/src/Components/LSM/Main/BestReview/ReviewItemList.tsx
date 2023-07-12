import Button from './Button';
import ReviewItem from './ReviewItem';

export default function ReviewItemList() {
  const items = ['1', '2', '3', '4', '5', '6', '7', '8', '9'];
  return (
    <div className="relative m-auto">
      <ul className="flex items-center justify-start pb-4 overflow-x-scroll lg:w-full snap-x scrollbar-hide">
        {items.map((item, idx) => (
          <ReviewItem key={idx} />
        ))}
      </ul>
      <Button />
    </div>
  );
}
