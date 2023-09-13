import useFetchItemsSales from 'hooks/admin/sales/useFetchItemsSales';
import TableContents from './TableContents';
import TableTitle from './TableTitle';
import calculateTotalSales from 'utils/sales/calculateTotalSales';
import addCommasToPrice from 'utils/addCommasToPrice';

export default function Table() {
  const sales = useFetchItemsSales();
  const totalSales = calculateTotalSales(sales);
  const commaTotalSales = addCommasToPrice(totalSales);
  console.log(sales);

  return (
    <>
      <div className="mb-2 text-lg text-right w-300">
        <span>총 매출액 :</span>
        <span className="font-bold"> {commaTotalSales}원</span>
      </div>
      <div className="bg-gray-100 border border-gray-400 rounded-t-lg w-300 h-128">
        <TableTitle />
        <ul>
          {sales.map((salesItem, idx) => (
            <TableContents
              key={salesItem.itemId}
              idx={idx}
              itemName={salesItem.itemName}
              itemPrice={salesItem.itemPrice}
              totalSales={totalSales}
            />
          ))}
        </ul>
      </div>
    </>
  );
}
