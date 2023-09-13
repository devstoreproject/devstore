import InquiryElement from './InquiryElement';
import InquiryRegister from './InquiryRegister';
import PaginationContainer from '../PaginationContainer';
import type { InquiryContentType } from '../Tab';

interface OwnProps {
  tab: number;
  inquiry: InquiryContentType[] | null;
  page: number;
  totalPage: number;
  setPage: React.Dispatch<React.SetStateAction<number>>;
  setInquiry: React.Dispatch<React.SetStateAction<InquiryContentType[] | null>>;
}

export default function InquiryTab({
  tab,
  inquiry,
  totalPage,
  setInquiry,
  page,
  setPage,
}: OwnProps) {
  return tab === 2 ? (
    <div className="bg-slate-100 rounded-lg">
      <p className="py-4 pl-8 border-b-2">상품 문의</p>
      <InquiryElement inquiry={inquiry} setInquiry={setInquiry} />
      {totalPage === 0 ? null : (
        <PaginationContainer
          page={page}
          setPage={setPage}
          totalPage={totalPage}
        />
      )}
      <InquiryRegister inquiry={inquiry} setInquiry={setInquiry} />
    </div>
  ) : null;
}
