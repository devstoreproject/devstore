import TagList from './TagList';

interface BestItemProps {
  title: string;
  category: any;
  price: string;
}

export default function BestItemInfo({
  title,
  category,
  price,
}: BestItemProps) {
  return (
    <div className="text-left">
      <p className="font-semibold">{title}</p>
      <div className="flex items-center justify-between mt-2">
        <ul className="flex items-center justify-center">
          {[category].map((item: any, idx: any) => (
            <TagList key={idx} item={item} />
          ))}
        </ul>
        <p className="font-semibold">{price}</p>
      </div>
    </div>
  );
}
