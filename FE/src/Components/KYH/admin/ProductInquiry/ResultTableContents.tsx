import type { Inquiry } from 'model/inquiry';

interface OwnProps extends Inquiry {
  idx: number;
  page: number;
}

export default function ResultTableContents({
  comment,
  answer,
  idx,
  page,
}: OwnProps) {
  return (
    <div
      className={`flex items-center justify-between h-12 px-6 rounded-t-lg border-b-gray-400 ${
        idx % 10 === 9 ? '' : 'border-b'
      }`}
    >
      <span className="w-8">{idx + 1 + page * 10}</span>
      <p className="text-center truncate w-80">데이터 필요</p>
      <p className="text-center truncate w-96">{comment}</p>
      <span className="text-center w-36">
        {answer === null ? '답변대기중' : '답변완료'}
      </span>
    </div>
  );
}
