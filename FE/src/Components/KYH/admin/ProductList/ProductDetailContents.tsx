import OptionTitle from './OptionTitle';
import OptionContents from './OptionContents';
import addCommasToPrice from 'utils/addCommasToPrice';
import type { ProductDetail } from 'model/product';

interface OwnProps {
  product: ProductDetail;
}

export interface option {
  optionId: number;
  optionName: string | null;
  additionalPrice: number;
  itemCount: number;
}
export default function ProductDetailContents({ product }: OwnProps) {
  const price = addCommasToPrice(product.itemPrice);
  return (
    <div className="w-144">
      <div className="flex items-center h-10 pl-4 text-sm bg-gray-100 border border-gray-300 rounded-tr-lg">
        {product.name}
      </div>
      <div className="flex items-center h-10 pl-4 text-sm bg-gray-100 border border-gray-300">
        {product.category}
      </div>
      <div className="flex items-center h-10 pl-4 text-sm bg-gray-100 border border-gray-300">
        {price}
      </div>
      <div className="flex items-center h-10 pl-4 text-sm bg-gray-100 border border-gray-300">
        {product.totalCount}
      </div>
      <div className="flex items-center h-10 pl-4 text-sm bg-gray-100 border border-gray-300">
        {product.salesQuantity}
      </div>
      <div className="flex flex-col text-sm bg-gray-100 border border-gray-300 rounded-br-lg">
        <OptionTitle />
        {product.optionList.map((option) => {
          return (
            <OptionContents
              key={option.optionId}
              optionDetail={option.optionDetail}
              additionalPrice={option.additionalPrice}
              itemCount={option.itemCount}
            />
          );
        })}
      </div>
    </div>
  );
}
