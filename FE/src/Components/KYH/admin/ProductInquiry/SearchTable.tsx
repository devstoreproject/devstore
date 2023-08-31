import type { Inquiry } from 'model/inquiry';
import SearchTableContents from './SearchTableContents';
import SearchTableTitle from './SearchTableTitle';

interface OwnProps {
  setInquirys: React.Dispatch<React.SetStateAction<Inquiry[]>>;
  defaultInquiry: Inquiry[];
}

export default function SearchTable({ setInquirys, defaultInquiry }: OwnProps) {
  return (
    <div className="flex h-12 bg-gray-100 border border-gray-400 rounded-lg w-300">
      <SearchTableTitle />
      <SearchTableContents
        setInquirys={setInquirys}
        defaultInquiry={defaultInquiry}
      />
    </div>
  );
}
