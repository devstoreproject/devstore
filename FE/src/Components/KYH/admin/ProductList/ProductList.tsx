import PaginationContainer from './PaginationContainer';
import Table from './Table';

export default function ProductList() {
  return (
    <div className="flex flex-col">
      <span className="mb-6 text-xl font-bold">판매 상품</span>
      <Table />
      <div className="flex items-center justify-between w-5/6 mt-6">
        <PaginationContainer />
        <div>
          <button className="h-10 px-4 mr-4 border border-gray-500 rounded-3xl">
            선택 된 상품 삭제하기
          </button>
          <button className="h-10 px-4 text-white bg-gray-700 rounded-3xl">
            상품 추가하기
          </button>
        </div>
      </div>
    </div>
  );
}
