import BestItemList from './BestProduct/BestItemList';
import MainTitle from './MainTitle';

export default function MainNewProduct() {
  return (
    <div className="w-full px-10">
      <MainTitle
        title="새로 나왔어요"
        icon="🔥"
        subTitle="NEW ITEM"
        classnames=""
      />
      <BestItemList />
    </div>
  );
}
