import type { ItemSales } from 'model/sales';
import addCommasToPrice from 'utils/addCommasToPrice';
import calculateSalesRate from 'utils/sales/calculateSalesRate';

interface OwnProps extends ItemSales {
  idx: number;
  totalSales: number;
}

export default function TableContents({
  itemName,
  itemPrice,
  idx,
  totalSales,
}: OwnProps) {
  const price = addCommasToPrice(itemPrice);
  const salesRate = calculateSalesRate(itemPrice, totalSales);
  return (
    <li className="flex items-center justify-between h-12 px-6 border-b border-b-gray-400">
      <span className="w-10 text-center">{idx + 1}</span>
      <p className="mr-4 text-center text-gray-700 truncate w-96">{itemName}</p>
      <span className="w-16 text-center">5</span>
      <span className="w-40 text-center">{price}원</span>
      <span className="w-16 text-center">{salesRate}%</span>
    </li>
  );
}
