import Pagination from '../../SharedComponent/Pagination';
import InquiryElement from './InquiryElement';

export default function InquiryTab({ tab }: { tab: number }) {
  return tab === 2 ? (
    <div className="rounded-lg bg-slate-100">
      <p className="py-4 pl-8">상품 문의</p>
      <InquiryElement />
      <InquiryElement />
      <InquiryElement />
      <InquiryElement />
      <Pagination />
    </div>
  ) : null;
}
