import ResultTableContents from './ResultTableContents';
import ResultTableTitle from './ResultTableTitle';

export default function ResultTable() {
  return (
    <div className="flex flex-col w-5/6 mt-6 mb-4 bg-gray-100 border border-gray-400 rounded-lg h-112">
      <ResultTableTitle />
      <ResultTableContents />
      <ResultTableContents />
      <ResultTableContents />
    </div>
  );
}
