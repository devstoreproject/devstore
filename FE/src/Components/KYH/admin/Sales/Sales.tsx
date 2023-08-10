import PaginationContainer from './PaginationContainer';
import SalesChart from './SalesChart';
import Table from './Table';

export default function Sales() {
  return (
    <div className="flex flex-col">
      <span className="mb-6 text-xl font-bold">총 매출내역</span>
      <SalesChart />
      <span className="mt-10 mb-6 text-xl font-bold">제품별 매출</span>
      <Table />
      <div className="flex items-center mt-6 w-300">
        <PaginationContainer />
      </div>
    </div>
  );
}
