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
    <div className="flex h-8 border-b-2 border-gray-300">
      <p className="p-1 px-4 text-center truncate border-r-2 border-r-gray-300 w-36">
        {optionName}
      </p>
      <p className="w-40 p-1 px-4 text-center truncate">{optionDetail}</p>
      <p className="w-40 p-1 px-4 text-center truncate border-gray-300 border-x-2">
        {price}
      </p>
      <p className="p-1 px-4 text-center truncate w-28">{itemCount}</p>
    </div>
  );
}
