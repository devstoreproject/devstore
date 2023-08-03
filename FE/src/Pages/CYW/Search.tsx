import SearchTool from 'Components/CYW/Search/SearchTool';
import SearchList from 'Components/CYW/Search/SearchList';

export default function Search() {
  return (
    <div className="flex flex-col pt-10">
      <div className="flex justify-center">
        <p className="flex text-slate-600 w-1/2">{`카테고리 > 검색`}</p>
      </div>
      <div className="flex flex-col items-center">
        <div className="flex justify-center w-full">
          <SearchTool />
        </div>
        <SearchList />
      </div>
    </div>
  );
}
