import type { SearchType } from 'Pages/CYW/Search';
import { useNavigate } from 'react-router-dom';

interface OwnProps {
  searchItem: SearchType | null;
  i: number;
}

export default function SearchItem({ searchItem, i }: OwnProps) {
  const navigate = useNavigate();
  const itemPrice = searchItem?.itemPrice;
  const itemName = searchItem?.name;
  const deliveryPrice = searchItem?.deliveryPrice;

  return (
    <div className="flex flex-col py-6 px-6 bg-white w-72 rounded-xl shadow-md">
      <img
        src={searchItem?.imageList[0]?.thumbnailPath}
        className="w-68 h-40 object-cover border rounded-xl mb-4 cursor-pointer"
        onClick={() => {
          navigate(`${Number(searchItem?.itemId)}`);
        }}
      />
      <p>{itemName}</p>
      <div>
        <span className="text-lg">{itemPrice?.toLocaleString('ko-KR')}</span>
        <span className="pl-2 line-through text-slate-500">
          {itemPrice?.toLocaleString('ko-KR')}
        </span>
        <span className="pl-2 text-slate-500">10%</span>
      </div>
      <p className="text-slate-500">
        {deliveryPrice === 0
          ? '무료 배송'
          : `배송비 ${String(deliveryPrice?.toLocaleString('ko-KR'))}원`}
      </p>
    </div>
  );
}
