import ResultTableContents from './ResultTableContents';
import ResultTableTitle from './ResultTableTitle';

export default function ResultTable() {
  const commonStyle = 'flex items-center justify-center border-box border-r-2';
  return (
    <div className="flex flex-col mt-6 mb-4 bg-gray-100 border border-gray-400 rounded-lg h-112 w-300">
      <ResultTableTitle commonStyle={commonStyle} />
      <ResultTableContents commonStyle={commonStyle} />
    </div>
  );
}
