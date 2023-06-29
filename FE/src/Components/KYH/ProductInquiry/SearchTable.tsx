import SearchTableContents from './SearchTableContents';
import SearchTableTitle from './SearchTableTitle';

export default function SearchTable() {
  return (
    <div className="flex w-5/6 h-40 bg-gray-100 border border-gray-400 rounded-lg">
      <SearchTableTitle />
      <SearchTableContents />
    </div>
  );
}
