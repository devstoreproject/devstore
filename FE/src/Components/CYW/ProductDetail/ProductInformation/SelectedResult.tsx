import { CiCirclePlus, CiCircleMinus } from 'react-icons/ci';
import { RxCross2 } from 'react-icons/rx';

interface OwnProps {
  name: string;
  price: number;
  productCount: number;
  setProductCount: React.Dispatch<React.SetStateAction<number>>;
  setSelectedValue: React.Dispatch<React.SetStateAction<number>>;
  selectedOptionDetail: string | null;
}

export default function SelectedResult({
  name,
  price,
  productCount,
  setProductCount,
  selectedOptionDetail,
  setSelectedValue,
}: OwnProps) {
  return (
    <div className="pt-5">
      <div className="flex flex-col border-box border-2 rounded-[20px] pt-3 pl-5 py-2 bg-slate-50">
        <div className="flex items-center">
          <span className="mb-2 font-bold">옵션 : {selectedOptionDetail}</span>
          <button
            className="ml-auto mr-4 -mt-3"
            onClick={() => {
              setSelectedValue(0);
            }}
          >
            <RxCross2 size={18} />
          </button>
        </div>
        <div className="flex items-center">
          <p>{name}</p>
        </div>
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

const btnSize = 24;
