import type { Inquiry } from 'model/inquiry';

export default function InquiryContent({ comment, answer, itemId }: Inquiry) {
  return (
    <div className="flex mb-2">
      <span>{comment}</span>
      <span className="ml-auto">
        {answer === null ? '답변대기중' : '답변완료'}
      </span>
    </div>
  );
}
