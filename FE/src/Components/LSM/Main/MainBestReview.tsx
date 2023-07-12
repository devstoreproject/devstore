import ReviewItemList from './BestReview/ReviewItemList';
import MainTitle from './MainTitle';

export default function MainBestReview() {
  return (
    <div className="w-full px-10">
      <MainTitle
        title="좋은 건 모두가 알아야죠"
        icon="👀"
        subTitle="BEST REVIEW"
      />
      <ReviewItemList />
    </div>
  );
}
