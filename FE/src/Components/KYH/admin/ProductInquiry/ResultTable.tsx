import type { Inquiry } from 'model/inquiry';
import ResultTableContents from './ResultTableContents';
import ResultTableTitle from './ResultTableTitle';

interface OwnProps {
  inquirys: Inquiry[];
  page: number;
}

export default function ResultTable({ inquirys, page }: OwnProps) {
  return (
    <div className="flex flex-col mt-6 mb-4 bg-gray-100 border border-gray-400 rounded-t-lg h-132.8 w-300">
      <ResultTableTitle />
      {inquirys.map((inquiry, idx) => (
        <ResultTableContents
          key={inquiry.questionId}
          comment={inquiry.comment}
          answer={inquiry.answer}
          idx={idx}
          page={page}
        />
      ))}
    </div>
  );
}
