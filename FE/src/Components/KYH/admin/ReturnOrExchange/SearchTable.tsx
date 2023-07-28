import SearchTableContents from './SearchTableContents';
import SearchTableTitle from './SearchTableTitle';

export default function SearchTable() {
  return (
    <div className="flex bg-gray-100 border border-gray-400 rounded-lg h-60 w-300">
      <SearchTableTitle />
      <SearchTableContents />
    </div>
  );
}
