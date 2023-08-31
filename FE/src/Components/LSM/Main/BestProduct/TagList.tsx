interface BestItemProps {
  item: string;
}

export default function TagList({ item }: BestItemProps) {
  return (
    <li className="px-2 py-1 mr-2 text-xs rounded-lg bg-tag-gray">{item}</li>
  );
}
