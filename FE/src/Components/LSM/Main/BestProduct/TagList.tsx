interface BestItemProps {
  tag: string;
}

export default function TagList({ tag }: BestItemProps) {
  return (
    <li className="px-2 py-1 mr-2 text-xs rounded-lg bg-tag-gray">{tag}</li>
  );
}
