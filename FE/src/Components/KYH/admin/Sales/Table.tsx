import useFetchItemsSalesPaging from 'hooks/admin/sales/useFetchItemsSalesPaging';
import TableContents from './TableContents';
import TableTitle from './TableTitle';
import calculateTotalSales from 'utils/sales/calculateTotalSales';
import addCommasToPrice from 'utils/addCommasToPrice';
import { useState } from 'react';
import PaginationContainer from '../ProductInquiry/PaginationContainer';

export default function Table() {
  const [page, setPage] = useState(0);
  const sales = useFetchItemsSalesPaging(page);
  const totalSales = calculateTotalSales(sales);
  const commaTotalSales = addCommasToPrice(totalSales);

  return (
    <>
      <div className="mb-2 text-lg text-right w-300">
        <span>총 매출액 :</span>
        <span className="font-bold"> {commaTotalSales}원</span>
      </div>
      <div className="bg-gray-100 border border-gray-400 rounded-t-lg w-300 h-132.8 mb-4">
        <TableTitle />
        <ul>
          {sales.length === 0 ? (
            <span className="flex justify-center items-center w-300 h-132.8">
              주문된 상품 내역이 없습니다.
            </span>
          ) : (
            sales.map((salesItem, idx) => (
              <TableContents
                key={salesItem.itemId}
                idx={idx}
                itemName={salesItem.itemName}
                itemPrice={salesItem.itemPrice}
                totalSales={totalSales}
              />
            ))
          )}
        </ul>
      </div>
      <PaginationContainer page={page} setPage={setPage} />
    </>
  );
}
