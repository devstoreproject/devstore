import useFetchProducts from 'hooks/admin/product/useFetchProducts';
import OrderListBtnContainer from './OrderListBtnContainer';
import PaginationContainer from './PaginationContainer';
import Table from './Table';

export default function ProductList() {
  const products = useFetchProducts();
  return (
    <div className="flex flex-col">
      <span className="mb-6 text-xl font-bold">판매 상품</span>
      <Table products={products} />
      <div className="flex items-center mt-6 w-300">
        <PaginationContainer />
        <OrderListBtnContainer />
      </div>
    </div>
  );
}
