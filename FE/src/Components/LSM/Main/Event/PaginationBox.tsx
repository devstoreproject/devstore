import PaginationItem from './PaginationItem';

export default function PaginationBox() {
  const items = ['1', '2', '3'];
  return (
    <div className="relative m-auto">
      <ul className="flex items-center justify-center pr-10 mt-10">
        {items.map((item, idx) => (
          <PaginationItem key={idx} item={item} />
        ))}
      </ul>
    </div>
  );
}
