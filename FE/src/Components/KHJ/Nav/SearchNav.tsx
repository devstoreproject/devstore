import Search from '../Common/Search';

export default function SearchNav() {
  return (
    <nav className="flex flex-col fixed top-0 right-0 w-80 h-full opacity-80 bg-white box-border pt-24 px-5 hidden">
      <Search />
      <ul>
        <li>최근검색어</li>
        <li>최근검색어</li>
        <li>최근검색어</li>
      </ul>
    </nav>
  );
}
