import OptionTitle from './OptionTitle';
import Option from './Option';
import addCommasToPrice from 'utils/addCommasToPrice';

interface OwnProps {
  product: {
    name: string;
    category: string;
    itemPrice: number;
    defaultCount: number;
    optionList: option[];
  };
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
    <div className="w-96">
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
        {product.defaultCount}
      </div>
      <div className="flex items-center h-10 pl-4 text-sm bg-gray-100 border border-gray-300">
        데이터 필요
      </div>
      <div className="flex flex-col h-48 text-sm bg-gray-100 border border-gray-300 rounded-br-lg">
        <OptionTitle />
        {product.optionList.slice(1).map((option) => {
          return (
            <Option
              key={option.optionId}
              name={option.optionName}
              additionalPrice={option.additionalPrice}
              itemCount={option.itemCount}
            />
          );
        })}
      </div>
    </div>
  );
}
