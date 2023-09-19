import { useState } from 'react';
import PaginationContainer from '../PaginationContainer';
import ResultTable from './ResultTable';
import SearchTable from './SearchTable';
import useFetchInquiryPaging from 'hooks/admin/inquiry/useFetchInquiryPaging';

export default function ProductInquiry() {
  const [page, setPage] = useState(0);
  const [checked, setChecked] = useState([true, true]);
  console.log(checked);
  const { inquirys, totalPages } = useFetchInquiryPaging(page, checked);

  return (
    <div className="flex flex-col w-full bg-light-gray">
      <span className="mb-6 text-xl font-bold">상품 문의</span>
      <SearchTable checked={checked} setChecked={setChecked} />
      <ResultTable inquirys={inquirys} page={page} />
      <PaginationContainer
        page={page}
        setPage={setPage}
        totalPages={totalPages}
      />
    </div>
  );
}
