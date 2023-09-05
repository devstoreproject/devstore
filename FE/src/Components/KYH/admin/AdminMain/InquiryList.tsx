import InquiryContent from './InquiryContent';
import useFetchInquiry from 'hooks/admin/inquiry/useFetchInquiry';

export default function InquiryList() {
  const inquiry = useFetchInquiry();

  return (
    <div className="flex flex-col h-48 px-6 py-4 mt-auto bg-white rounded-lg shadow-signBox w-200">
      <span className="mb-2 text-lg font-bold">문의내역</span>
      {inquiry.slice(0, 4).map((content) => (
        <InquiryContent
          key={content.questionId}
          itemId={content.itemId}
          comment={content.comment}
          answer={content.answer}
        />
      ))}
    </div>
  );
}
