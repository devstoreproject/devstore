import type { Product } from 'model/auth';

interface OwnProps extends Product {
  setIsDetailModalOpen: React.Dispatch<React.SetStateAction<boolean>>;
  setProductId: React.Dispatch<React.SetStateAction<number>>;
  setCheckedId: React.Dispatch<React.SetStateAction<number[]>>;
}

export default function TableContents({
  itemId,
  name,
  defaultCount,
  totalCount,
  itemPrice,
  setIsDetailModalOpen,
  setProductId,
  setCheckedId,
}: OwnProps) {
  return (
    <li className="flex items-center justify-between h-12 px-6 border-b border-b-gray-400">
      <input
        type="checkbox"
        className="w-5 h-5"
        onChange={(e) => {
          if (e.target.checked) {
            setCheckedId((prev) => [...prev, itemId]);
          } else {
            setCheckedId((prev) => prev.filter((id) => id !== itemId));
          }
        }}
      />
      <span>{itemId}</span>
      <input
        className="text-center text-gray-700 underline truncate cursor-pointer w-120 decoration-2 underline-offset-4"
        value={name}
        type="button"
        onClick={() => {
          setIsDetailModalOpen(true);
          setProductId(itemId);
        }}
      />
      <span className="w-16 text-center">{totalCount}</span>
      <span className="w-12 text-center">데이터 필요</span>
      <span className="w-12 text-center">{totalCount}</span>
      <span className="w-24 text-center">{itemPrice}</span>
    </li>
  );
}
