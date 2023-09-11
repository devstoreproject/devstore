import type { Option } from 'model/product';
import addCommasToPrice from 'utils/addCommasToPrice';

export default function OptionContents({
  optionName,
  optionDetail,
  additionalPrice,
  itemCount,
}: Option) {
  const price = addCommasToPrice(additionalPrice);
  return (
    <div className="flex border-b-2 border-gray-300">
      <div className="flex items-center justify-center h-8 border-r-2 border-gray-300 w-36">
        {optionName}
      </div>
      <div className="flex items-center justify-center w-40 h-8">
        {optionDetail}
      </div>
      <div className="flex items-center justify-center w-40 h-8 border-gray-300 border-x-2">
        {price}
      </div>
      <div className="flex items-center justify-center h-8 w-28">
        {itemCount}
      </div>
    </div>
  );
}
