import { useEffect } from 'react';
import OrderListBtnContainer from './OrderListBtnContainer';
import PaginationContainer from './PaginationContainer';
import Table from './Table';
import api from 'api';

export default function ProductList() {
  useEffect(() => {
    api
      .get('/api/items')
      .then((res) => {
        console.log(res);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);
  return (
    <div className="flex flex-col">
      <span className="mb-6 text-xl font-bold">판매 상품</span>
      <Table />
      <div className="flex items-center mt-6 w-300">
        <PaginationContainer />
        <OrderListBtnContainer />
      </div>
    </div>
  );
}
