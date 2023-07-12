import InquiryContent from './InquiryContent';

export default function InquiryList() {
  return (
    <div className="flex flex-col w-full h-48 px-6 py-4 mt-auto bg-white rounded-lg shadow-signBox">
      <span className="mb-4 text-lg font-bold">문의내역</span>
      <InquiryContent />
      <InquiryContent />
      <InquiryContent />
    </div>
  );
}