import OrderListTable from './OrderListTable';
import OrderListTitle from './OrderListTitle';

export default function OrderList() {
  return (
    <div className="flex flex-col px-6 py-4 mt-6 bg-white rounded-lg w-358 h-132.8 shadow-signBox">
      <OrderListTitle />
      <OrderListTable />
    </div>
  );
}
