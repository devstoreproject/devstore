interface EventItemProps {
  item: string;
}

export default function PaginationItem({ item }: EventItemProps) {
  return (
    <li className="flex items-center justify-center w-3 h-3 mr-2 rounded-full cursor-pointer bg-tab-gray first:bg-light-black"></li>
  );
}
