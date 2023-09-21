import EventItem from './EventItem';
import PaginationBox from './PaginationBox';

export default function EventItemList() {
  const items = ['1', '2', '3'];
  return (
    <div className="relative m-auto">
      <ul className="flex items-center justify-start lg:w-full">
        {items.map((item, idx) => (
          <EventItem key={idx} />
        ))}
      </ul>
      <PaginationBox />
    </div>
  );
}
