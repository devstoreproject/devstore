import type { Product, Option } from 'model/product';
import { useDispatch } from 'react-redux';
import { setItemId } from 'store/modules/setItemId';
import addCommasToPrice from 'utils/addCommasToPrice';

interface OwnProps extends Product {
  idx: number;
  page: number;
  optionList: Option[];
  setIsDetailModalOpen: React.Dispatch<React.SetStateAction<boolean>>;
  setProductId: React.Dispatch<React.SetStateAction<number>>;
  setCheckedId: React.Dispatch<React.SetStateAction<number[]>>;
}

export default function TableContents({
  idx,
  itemId,
  name,
  totalCount,
  optionList,
  itemPrice,
  salesQuantity,
  setIsDetailModalOpen,
  setProductId,
  setCheckedId,
  page,
}: OwnProps) {
  const price = addCommasToPrice(itemPrice);
  const dispatch = useDispatch();
  const stock = totalCount - salesQuantity;

  return (
    <li
      className={`flex items-center justify-between h-12 px-6 border-b-gray-400 hover:bg-gray-300 hover:font-bold ${
        idx % 10 === 9 ? '' : 'border-b'
      }`}
    >
      <input
        type="checkbox"
        className="w-5 h-5"
        onChange={(e) => {
          if (e.target.checked) {
            setCheckedId((prev) => [...prev, itemId]);
            dispatch(setItemId(itemId));
          } else {
            setCheckedId((prev) => prev.filter((id) => id !== itemId));
          }
        }}
      />
      <span className="w-5">{idx + 1 + page * 10}</span>
      <input
        className="text-center text-gray-700 truncate cursor-pointer w-100 hover:underline"
        value={name}
        type="button"
        onClick={() => {
          setIsDetailModalOpen(true);
          setProductId(itemId);
        }}
      />
      <span className="w-20 text-center">{totalCount}개</span>
      <span className="w-16 text-center">{salesQuantity}개</span>
      <span className="w-20 text-center">{stock}개</span>
      <span className="text-center w-28">{price}원</span>
    </li>
  );
}
