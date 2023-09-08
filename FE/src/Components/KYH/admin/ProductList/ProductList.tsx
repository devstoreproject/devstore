import { useState } from 'react';
import OrderListBtnContainer from './OrderListBtnContainer';
import Table from './Table';
import ProductDetail from './ProductDetail';
import { CgCloseR } from 'react-icons/cg';
import useFetchProductsPaging from 'hooks/admin/product/useFetchProductsPaging';
import PaginationContainer from '../ProductInquiry/PaginationContainer';
// import useFetchItemsSales from 'hooks/admin/sales/useFetchItemsSales';

export default function ProductList() {
  const [isDetailModalOpen, setIsDetailModalOpen] = useState(false);
  const [productId, setProductId] = useState(0);
  const [checkedId, setCheckedId] = useState([0]);
  const [page, setPage] = useState(0);

  const { products, setProducts } = useFetchProductsPaging(page);
  // const itemSales = useFetchItemsSales();
  // console.log(itemSales);

  return (
    <div className="relative flex flex-col w-300">
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
            <CgCloseR className="absolute left-[30.5rem] bottom-32 z-10 bg-white rounded-md border w-6 h-6 cursor-pointer" />
          </label>
        </>
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
        <PaginationContainer page={page} setPage={setPage} />
        <OrderListBtnContainer
          checkedId={checkedId}
          setProducts={setProducts}
        />
      </div>
    </div>
  );
}
