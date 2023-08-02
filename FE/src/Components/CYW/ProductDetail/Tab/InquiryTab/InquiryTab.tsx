import InquiryElement from './InquiryElement';
import InquiryRegister from './InquiryRegister';
import PaginationContainer from './PaginationContainer';

export default function InquiryTab({ tab }: { tab: number }) {
  return tab === 1 ? (
    <div className="bg-slate-100 rounded-lg">
      <p className="py-4 pl-8 border-b-2">상품 문의</p>
      <InquiryElement />
      <InquiryElement />
      <InquiryElement />
      <InquiryElement />
      <PaginationContainer />
      <InquiryRegister />
    </div>
  ) : null;
}
