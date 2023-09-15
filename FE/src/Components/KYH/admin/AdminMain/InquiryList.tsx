import InquiryContent from './InquiryContent';
import useFetchInquiry from 'hooks/admin/inquiry/useFetchInquiry';
import InquiryListTitle from './InquiryListTitle';

export default function InquiryList() {
  const inquiry = useFetchInquiry();

  return (
    <div className="flex flex-col h-48 px-6 py-4 mt-auto bg-white rounded-lg shadow-signBox w-200">
      <InquiryListTitle />
      {inquiry.length === 0 ? (
        <span className="flex items-center justify-center h-48 w-188">
          등록된 상품문의 내역이 없습니다.
        </span>
      ) : (
        inquiry
          .slice(0, 4)
          .map((content) => (
            <InquiryContent
              key={content.questionId}
              itemId={content.itemId}
              comment={content.comment}
              answer={content.answer}
            />
          ))
      )}
    </div>
  );
}
