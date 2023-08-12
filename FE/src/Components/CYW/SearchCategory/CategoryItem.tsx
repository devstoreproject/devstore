import type { CategoryContent } from 'Pages/CYW/SearchCategory';
import { useNavigate } from 'react-router-dom';

interface OwnProps {
  categoryItem: CategoryContent;
}

export default function CategoryItem({ categoryItem }: OwnProps) {
  const navigate = useNavigate();
  return (
    <button
      className="flex px-8"
      onClick={() => {
        navigate(`${categoryItem.itemId}`);
      }}
    >
      <div className="flex flex-col py-6 px-6 mb-12 bg-white w-80 rounded-xl shadow-md">
        <img
          src="https://item.sellpro.co.kr/data/item/125/5565685125_550.jpg"
          className="w-68 h-40 object-cover border rounded-xl mb-4"
        />
        <p className="text-left">{categoryItem.name}</p>
        <div className="text-left">
          <span className="text-xl">
            {(categoryItem.itemPrice * 0.9).toLocaleString('ko-KR')}
          </span>
          <span className="pl-2 line-through text-slate-500">
            {categoryItem.itemPrice.toLocaleString('ko-KR')}
          </span>
          <span className="pl-2 text-slate-500">10%</span>
        </div>
        <p className="text-slate-500 text-left">
          {categoryItem.deliveryPrice === 0
            ? '무료배송'
            : `배송비 ${categoryItem.deliveryPrice}원`}
        </p>
      </div>
    </button>
  );
}
