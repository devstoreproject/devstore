import { useEffect, useState } from 'react';
import PaginationContainer from './PaginationContainer';
import ResultTable from './ResultTable';
import SearchTable from './SearchTable';
import type { Inquiry } from 'model/inquiry';
import useFetchInquiryPaging from 'hooks/admin/inquiry/useFetchInquiryPaging';

export default function ProductInquiry() {
  const [inquirys, setInquirys] = useState<Inquiry[]>([]);
  const [page, setPage] = useState(0);
  const defaultInquiry = useFetchInquiryPaging(page);

  useEffect(() => {
    setInquirys(defaultInquiry);
  }, [setInquirys, defaultInquiry]);

  return (
    <div className="flex flex-col w-full bg-light-gray">
      <span className="mb-6 text-xl font-bold">상품 문의</span>
      <SearchTable setInquirys={setInquirys} defaultInquiry={defaultInquiry} />
      <ResultTable inquirys={inquirys} page={page} />
      <PaginationContainer page={page} setPage={setPage} />
    </div>
  );
}
