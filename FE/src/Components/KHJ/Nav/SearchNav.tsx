import Search from '../Common/Search';
import type { SearchNavFold } from '../Type/NavSearchType';

export default function SearchNav({
  searchOpen,
  setSearchOpen,
}: SearchNavFold): React.ReactElement {
  return (
    <nav
      className={`flex flex-col fixed top-0 right-0 w-80 h-full opacity-90 bg-white box-border pt-24 px-5 z-20 transition-transform sm:w-full sm:translate-x-0 ${
        searchOpen
          ? 'translate-x-0 sm:translate-y-0 sm:w-full sm:top-0'
          : 'translate-x-80 sm:-translate-y-full sm:-top-full '
      }`}
    >
      <button
        className="absolute top-7 right-5 text-2xl"
        onClick={() => {
          setSearchOpen(false);
        }}
      >
        X
      </button>
      <Search />
      <ul>
        <li>최근검색어</li>
        <li>최근검색어</li>
        <li>최근검색어</li>
      </ul>
    </nav>
  );
}
