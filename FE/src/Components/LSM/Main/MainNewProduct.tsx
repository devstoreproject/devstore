import MainTitle from './MainTitle';
import NewItemList from './NewProduct/NewItemList';

interface MainProp {
  newItemsData: any;
}

export default function MainNewProduct({ newItemsData }: MainProp) {
  return (
    <div className="w-full px-10">
      <MainTitle
        title="새로 나왔어요"
        icon="🔥"
        subTitle="NEW ITEM"
        classnames=""
      />
      <NewItemList newItemsData={newItemsData} />
    </div>
  );
}
