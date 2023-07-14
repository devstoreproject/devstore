import Pagination from '../../SharedComponent/Pagination';
import InquiryElement from './InquiryElement';

export default function InquiryTab({ tab }: { tab: number }) {
  if (tab === 2) {
    return (
      <div className="bg-slate-100 rounded-lg">
        <p className="py-4 pl-8">상품 문의</p>
        <InquiryElement />
        <InquiryElement />
        <InquiryElement />
        <InquiryElement />
        <Pagination />
      </div>
    );
  }
}
