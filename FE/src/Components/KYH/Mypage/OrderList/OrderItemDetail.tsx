import type { OrderItem } from 'model/order';
import addCommasToPrice from 'utils/addCommasToPrice';

interface OwnProps extends OrderItem {
  idx: number;
}

export default function OrderItemDetail({
  idx,
  itemName,
  itemCount,
  itemPrice,
  discountPrice,
}: OwnProps) {
  const totalPrice = addCommasToPrice(discountPrice);
  const price = addCommasToPrice(itemPrice);
  return (
    <div className="flex items-center border-b border-gray-300 h-14">
      <span className="w-4 ml-2 text-sm text-center text-gray-500">
        {idx + 1}
      </span>
      <span className="text-sm text-center truncate w-80">{itemName}</span>
      <span className="w-40 ml-4 text-sm text-center">{totalPrice}원</span>
      <span className="ml-4 mr-2 text-sm text-end w-60">
        {itemCount}개 (개당 {price}원)
      </span>
    </div>
  );
}
