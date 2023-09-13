// import Button from './Button';
import ReviewItem from './ReviewItem';

interface ReviewItemListProps {
  reviewData: any;
}

export default function ReviewItemList({ reviewData }: ReviewItemListProps) {
  return (
    <div className="relative m-auto">
      <ul className="flex items-center justify-start pb-4 overflow-x-scroll lg:w-full snap-x scrollbar-hide">
        {reviewData.map((data: any) => (
          <ReviewItem
            key={data.reviewId}
            id={data.itemId}
            category={data.category}
            image={data.image}
            comment={data.comment}
            userName={data.userName}
            modifiedAt={data.modifiedAt}
          />
        ))}
      </ul>
      {/* <Button /> */}
    </div>
  );
}
