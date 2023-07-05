import SearchTableContents from './SearchTableContents';
import SearchTableTitle from './SearchTableTitle';

export default function SearchTable() {
  return (
    <div className="flex h-40 bg-gray-100 border border-gray-400 rounded-lg w-250">
      <SearchTableTitle />
      <SearchTableContents />
    </div>
  );
}
