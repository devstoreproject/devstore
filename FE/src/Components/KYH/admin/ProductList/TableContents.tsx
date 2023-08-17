import type { Product } from 'model/auth';

export default function TableContents({
  itemId,
  name,
  defaultCount,
  totalCount,
  itemPrice,
}: Product) {
  return (
    <li className="flex items-center justify-between h-12 px-6 border-b border-b-gray-400">
      <input type="checkbox" className="w-5 h-5" />
      <span>{itemId}</span>
      <p className="text-center text-gray-700 truncate w-120">{name}</p>
      <span className="w-16 text-center">{totalCount}</span>
      <span className="w-12 text-center">데이터 필요</span>
      <span className="w-12 text-center">{totalCount}</span>
      <span className="w-24 text-center">{itemPrice}</span>
    </li>
  );
}
