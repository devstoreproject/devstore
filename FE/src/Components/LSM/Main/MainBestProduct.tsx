import BestItemList from './BestProduct/BestItemList';
import MainTitle from './MainTitle';

interface MainProp {
  bestItemsData: any;
}

export default function MainBestProduct({ bestItemsData }: MainProp) {
  return (
    <div className="w-full px-10">
      <MainTitle title="개발자들이 많이 찾는" icon="👓" subTitle="BEST ITEM" />
      <BestItemList bestItemsData={bestItemsData} />
    </div>
  );
}
