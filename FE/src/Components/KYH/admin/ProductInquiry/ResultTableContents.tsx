import type { Inquiry } from 'model/inquiry';

interface OwnProps extends Inquiry {
  idx: number;
  page: number;
  name: string;
  setIsModalOpen: React.Dispatch<React.SetStateAction<boolean>>;
  setInquiryId: React.Dispatch<React.SetStateAction<number>>;
  setProductName: React.Dispatch<React.SetStateAction<string>>;
}

export default function ResultTableContents({
  questionId,
  comment,
  answer,
  idx,
  page,
  name,
  setIsModalOpen,
  setInquiryId,
  setProductName,
}: OwnProps) {
  return (
    <button
      className={`flex items-center justify-between h-12 px-6 rounded-t-lg border-b-gray-400 hover:bg-gray-200 hover:font-bold ${
        idx % 10 === 9 ? '' : 'border-b'
      }`}
      onClick={() => {
        setIsModalOpen(true);
        if (questionId !== undefined) setInquiryId(questionId);
        setProductName(name);
      }}
    >
      <span className="w-8">{idx + 1 + page * 10}</span>
      <p className="text-center truncate w-80">{name}</p>
      <p className="text-center truncate w-80">{comment}</p>
      <span className="pl-10 text-center w-36">
        {answer === null ? '답변대기중' : '답변완료'}
      </span>
    </button>
  );
}
