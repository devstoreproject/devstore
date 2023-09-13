import type { OptionListType } from 'Pages/CYW/ProductDetail';
import { CiCirclePlus, CiCircleMinus } from 'react-icons/ci';
import { RxCross2 } from 'react-icons/rx';

interface OwnProps {
  name: string;
  price: number;
  option: OptionListType[];
  productCount: number;
  selectedValue: number;
  setProductCount: React.Dispatch<React.SetStateAction<number>>;
}

export default function SelectedResult({
  name,
  price,
  option,
  productCount,
  setProductCount,
  selectedValue,
}: OwnProps) {
  const btnSize = 24;
  return (
    <div className="pt-5">
      <div className="border-box border-2 rounded-[20px] pt-3 pl-5 py-2 bg-slate-50">
        <div className="flex items-center">
          <p>{name}</p>
          <div className="ml-auto pr-4">
            <RxCross2 size={18} />
          </div>
        </div>
        {option.map((optionItem, i) => (
          <div className="flex pt-1" key={i}>
            <span className="font-light">
              {optionItem.optionName}: {selectedValue}
            </span>
          </div>
        ))}
        <div className="flex items-center pt-2">
          <button
            onClick={() => {
              setProductCount((prevCount) =>
                prevCount === 1 ? 1 : prevCount - 1
              );
            }}
          >
            <CiCircleMinus size={btnSize} />
          </button>
          <p className="mx-4">{productCount}</p>
          <button
            onClick={() => {
              setProductCount((prevCount) => prevCount + 1);
            }}
          >
            <CiCirclePlus size={btnSize < 0 ? btnSize : btnSize} />
          </button>
          <span className="ml-auto mr-4">
            {(price * 0.9 * productCount).toLocaleString('ko-KR')}
          </span>
        </div>
      </div>
    </div>
  );
}
