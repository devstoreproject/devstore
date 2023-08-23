import { useState } from 'react';
import useFetchProducts from 'hooks/admin/product/useFetchProducts';
import OrderListBtnContainer from './OrderListBtnContainer';
import PaginationContainer from './PaginationContainer';
import Table from './Table';
import ProductDetail from './ProductDetail';
import { CgCloseR } from 'react-icons/cg';

export default function ProductList() {
  const products = useFetchProducts();

  const [isDetailModalOpen, setIsDetailModalOpen] = useState(false);
  const [productId, setProductId] = useState(0);
  const [checkedId, setCheckedId] = useState([0]);

  return (
    <div className="relative flex flex-col">
      <span className="mb-6 text-xl font-bold">판매 상품</span>
      {isDetailModalOpen ? (
        <>
          <ProductDetail productId={productId} />
          <label>
            <input
              type="button"
              className="hidden"
              onClick={() => {
                setIsDetailModalOpen(false);
              }}
            />
            <CgCloseR className="absolute left-[30.5rem] bottom-20 z-10 bg-white rounded-md border w-6 h-6 cursor-pointer" />
          </label>
        </>
      ) : null}
      <Table
        products={products}
        isDetailModalOpen={isDetailModalOpen}
        setIsDetailModalOpen={setIsDetailModalOpen}
        setProductId={setProductId}
        setCheckedId={setCheckedId}
      />
      <div className="flex items-center mt-6 w-300">
        <PaginationContainer />
        <OrderListBtnContainer checkedId={checkedId} />
      </div>
    </div>
  );
}
