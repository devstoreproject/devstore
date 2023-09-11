import type { SearchType } from 'Pages/CYW/Search';
import SearchItem from './SearchItem';

interface OwnProps {
  search: SearchType[] | null;
}

export default function SearchList({ search }: OwnProps) {
  if (search === null) {
    return <div className="pt-10">입력해라 닝겐</div>;
  }
  return (
    <div className="flex flex-wrap gap-x-8 gap-y-8 pt-20 pb-20">
      {search?.map((searchItem, i) => (
        <div key={searchItem.itemId}>
          <SearchItem searchItem={searchItem} i={i} />
        </div>
      ))}
    </div>
  );
}
