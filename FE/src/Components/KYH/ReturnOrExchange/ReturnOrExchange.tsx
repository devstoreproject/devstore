import PaginationContainer from './PaginationContainer';
import ResultTable from './ResultTable';
import SearchTable from './SearchTable';

export default function ReturnOrExchange() {
  return (
    <div className="flex flex-col w-screen min-h-screen pt-20 pl-48 bg-light-gray">
      <span className="mb-6 text-xl font-bold">반품 / 교환 신청 확인</span>
      <SearchTable />
      <ResultTable />
      <PaginationContainer />
    </div>
  );
}
