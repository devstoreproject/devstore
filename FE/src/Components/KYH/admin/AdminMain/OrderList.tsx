import OrderListTable from './OrderListTable';

export default function OrderList() {
  return (
    <div className="flex flex-col px-6 py-4 mt-6 bg-white rounded-lg w-358 h-100 shadow-signBox">
      <span className="mb-4 text-lg font-bold">주문내역</span>
      <OrderListTable />
    </div>
  );
}