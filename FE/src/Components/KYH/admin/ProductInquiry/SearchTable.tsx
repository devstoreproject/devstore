import SearchTableContents from './SearchTableContents';
import SearchTableTitle from './SearchTableTitle';

interface OwnProps {
  checked: boolean[];
  setChecked: React.Dispatch<React.SetStateAction<boolean[]>>;
}

export default function SearchTable({ checked, setChecked }: OwnProps) {
  return (
    <div className="flex h-12 bg-gray-100 border border-gray-400 rounded-lg w-300">
      <SearchTableTitle />
      <SearchTableContents checked={checked} setChecked={setChecked} />
    </div>
  );
}
