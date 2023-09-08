import type { OrderItem } from 'model/order';
import addCommasToPrice from 'utils/addCommasToPrice';

interface OwnProps extends OrderItem {
  idx: number;
}

export default function DetailContents({
  idx,
  itemName,
  itemCount,
  itemPrice,
}: OwnProps) {
  const price = addCommasToPrice(itemPrice);
  const totalPrice = addCommasToPrice(itemPrice * itemCount);
  return (
    <div className="flex items-center h-10 border-b border-gray-300">
      <span className="w-20">{idx + 1}</span>
      <p className="truncate w-100">{itemName}</p>
      <span className="w-60">{itemCount}</span>
      <span className="w-60">{price}</span>
      <span className="w-60">{totalPrice}</span>
    </div>
  );
}
