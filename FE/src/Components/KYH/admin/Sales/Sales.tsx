import SalesChart from './SalesChart';
import Table from './Table';

export default function Sales() {
  return (
    <div className="flex flex-col">
      <span className="mb-6 text-xl font-bold">총 매출내역</span>
      <SalesChart />
      <span className="mt-10 text-xl font-bold">제품별 매출</span>
      <Table />
    </div>
  );
}
