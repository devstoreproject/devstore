import type { OrderItem } from 'model/order';
import DetailTitle from './DetailTitle';
import DetailContents from './DetailContents';

interface OwnProps {
  orderItemList: OrderItem[];
}

export default function ResultTableContentsDetail({ orderItemList }: OwnProps) {
  return (
    <div className="box-border flex flex-col items-start w-full pt-2 mt-4 border-t border-gray-400">
      <span className="ml-12 text-sm font-bold">상세정보</span>
      <DetailTitle />
      {orderItemList.map((orderItem, idx) => (
        <DetailContents
          key={idx}
          idx={idx}
          itemName={orderItem.itemName}
          itemCount={orderItem.itemCount}
          itemPrice={orderItem.itemPrice}
        />
      ))}
    </div>
  );
}
