import PaginationContainer from './PaginationContainer';
import Table from './Table';

export default function Sales() {
  return (
    <div className="flex flex-col">
      <span className="mb-6 text-xl font-bold">판매 상품</span>
      <Table />
      <div className="flex items-center mt-6 w-300">
        <PaginationContainer />
      </div>
    </div>
  );
}
