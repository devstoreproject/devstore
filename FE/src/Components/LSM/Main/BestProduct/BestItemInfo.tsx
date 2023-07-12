import TagList from './TagList';

interface BestItemProps {
  title: string;
  tags: string[];
  price: string;
}

export default function BestItemInfo({ title, tags, price }: BestItemProps) {
  return (
    <div className="text-left">
      <p className="font-semibold">{title}</p>
      <div className="flex items-center justify-between mt-2">
        <ul className="flex items-center justify-center">
          {tags.map((tag, idx) => (
            <TagList key={idx} tag={tag} />
          ))}
        </ul>
        <p className="font-semibold">{price}</p>
      </div>
    </div>
  );
}
