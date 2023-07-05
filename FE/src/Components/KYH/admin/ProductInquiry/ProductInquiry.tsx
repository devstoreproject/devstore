import PaginationContainer from './PaginationContainer';
import ResultTable from './ResultTable';
import SearchTable from './SearchTable';

export default function ProductInquiry() {
  return (
    <div className="flex flex-col w-full bg-light-gray">
      <span className="mb-6 text-xl font-bold">상품 문의</span>
      <SearchTable />
      <ResultTable />
      <PaginationContainer />
    </div>
  );
}
