import ReviewItemList from './BestReview/ReviewItemList';
import MainTitle from './MainTitle';

interface MainProp {
  reviewData: any;
}

export default function MainBestReview({ reviewData }: MainProp) {
  return (
    <div className="w-full px-10">
      <MainTitle
        title="좋은 건 모두가 알아야죠"
        icon="👀"
        subTitle="BEST REVIEW"
      />
      <ReviewItemList reviewData={reviewData} />
    </div>
  );
}
