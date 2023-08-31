import TagList from '../BestProduct/TagList';

interface NewItemProps {
  title: string;
  category: any;
  price: string;
}

export default function NewItemInfo({ title, category, price }: NewItemProps) {
  return (
    <div className="text-left">
      <p className="font-semibold">{title}</p>
      <div className="flex items-center justify-between mt-2">
        <ul className="flex items-center justify-center">
          {[category].map((item, idx) => (
            <TagList key={idx} item={item} />
          ))}
        </ul>
        <p className="font-semibold">{price} 원</p>
      </div>
    </div>
  );
}
