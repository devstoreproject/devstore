import type { Option } from 'model/product';
import addCommasToPrice from 'utils/addCommasToPrice';

export default function OptionContents({
  optionDetail,
  additionalPrice,
  itemCount,
}: Option) {
  const price = addCommasToPrice(additionalPrice);
  return (
    <div className="flex h-8 border-b-2 border-gray-300">
      <p className="pt-1 text-center truncate w-80">{optionDetail}</p>
      <p className="w-40 pt-1 text-center truncate border-gray-300 border-x-2">
        {price}
      </p>
      <p className="pt-1 text-center truncate w-28">{itemCount}</p>
    </div>
  );
}
