import InquiryElement from './InquiryElement';
import InquiryRegister from './InquiryRegister';
import PaginationContainer from './PaginationContainer';
import type { InquiryContentType } from '../Tab';

interface OwnProps {
  tab: number;
  inquiry: InquiryContentType[] | null;
  setInquiry: React.Dispatch<React.SetStateAction<InquiryContentType[] | null>>;
}

export default function InquiryTab({ tab, inquiry, setInquiry }: OwnProps) {
  return tab === 2 ? (
    <div className="bg-slate-100 rounded-lg">
      <p className="py-4 pl-8 border-b-2">상품 문의</p>
      <InquiryElement inquiry={inquiry} setInquiry={setInquiry} />
      <PaginationContainer />
      <InquiryRegister inquiry={inquiry} setInquiry={setInquiry} />
    </div>
  ) : null;
}
