import { useState } from 'react';
import OrderListBtnContainer from './OrderListBtnContainer';
import Table from './Table';
import ProductDetail from './ProductDetail';
import useFetchProductsPaging from 'hooks/admin/product/useFetchProductsPaging';
import PaginationContainer from '../PaginationContainer';

export default function ProductList() {
  const [isDetailModalOpen, setIsDetailModalOpen] = useState(false);
  const [productId, setProductId] = useState(0);
  const [checkedId, setCheckedId] = useState<number[]>([]);
  const [page, setPage] = useState(0);

  const { products, setProducts, totalPages } = useFetchProductsPaging(page);

  return (
    <div className="relative flex flex-col w-300">
      <span className="mb-6 text-xl font-bold">판매 상품</span>
      {isDetailModalOpen ? (
        <ProductDetail
          productId={productId}
          setIsDetailModalOpen={setIsDetailModalOpen}
        />
      ) : null}
      <Table
        products={products}
        isDetailModalOpen={isDetailModalOpen}
        setIsDetailModalOpen={setIsDetailModalOpen}
        setProductId={setProductId}
        setCheckedId={setCheckedId}
        page={page}
      />
      <div className="flex flex-col mt-2">
        <PaginationContainer
          page={page}
          setPage={setPage}
          totalPages={totalPages}
        />
        <OrderListBtnContainer
          checkedId={checkedId}
          setProducts={setProducts}
        />
      </div>
    </div>
  );
}
