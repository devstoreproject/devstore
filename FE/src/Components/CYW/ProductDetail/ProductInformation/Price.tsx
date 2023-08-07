interface PriceProps {
  price: number;
}

export default function Price({ price }: PriceProps) {
  return (
    <div className="flex justify-end pt-2">
      <span className="text-right line-through text-slate-400 pr-2 font-normal">
        {price.toLocaleString('ko-KR')}
      </span>
      <span className="font-bold">{(price * 0.9).toLocaleString('ko-KR')}</span>
    </div>
  );
}
