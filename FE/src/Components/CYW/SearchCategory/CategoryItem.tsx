import type { CategoryContent } from 'Pages/CYW/Products';
import { useNavigate } from 'react-router-dom';
import { LuImageOff } from 'react-icons/lu';

interface OwnProps {
  categoryItem: CategoryContent;
}

export default function CategoryItem({ categoryItem }: OwnProps) {
  const navigate = useNavigate();
  const imgArrJudge: number | undefined = categoryItem.imageList.length;
  const iconSize: number = 100;

  return (
    <div className="flex flex-col py-6 px-6 bg-white w-80 rounded-xl shadow-md">
      {imgArrJudge !== undefined && imgArrJudge > 0 ? (
        <img
          src={categoryItem.imageList[0]?.thumbnailPath}
          className="w-68 h-40 object-cover border rounded-xl mb-4 cursor-pointer"
          onClick={() => {
            navigate(`${categoryItem.itemId}`);
          }}
        />
      ) : (
        <LuImageOff
          size={iconSize}
          style={{
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
          }}
          onClick={() => {
            navigate(`${categoryItem.itemId}`);
          }}
        />
      )}
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
  );
}
