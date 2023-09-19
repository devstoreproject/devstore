import ResultTable from 'Components/CYW/OrderedList/ResultTable';
import PaginationContainer from 'Components/KYH/admin/PaginationContainer';
import useFetchOrdersPaging from 'hooks/admin/orderedList/useFetchOrdersPaging';
import { useState } from 'react';
import { BsBoxSeam } from 'react-icons/bs';

export default function OrderedList() {
  const [page, setPage] = useState(0);
  const { orders, totalPages } = useFetchOrdersPaging(page);
  return (
    <div className="flex flex-col w-full bg-light-gray">
      <div className="flex items-center mb-6">
        <BsBoxSeam size={25} />
        <h1 className="pl-2 text-xl font-bold">주문 된 상품 내역</h1>
      </div>
      <ResultTable orders={orders} />
      <PaginationContainer
        page={page}
        setPage={setPage}
        totalPages={totalPages}
      />
    </div>
  );
}
