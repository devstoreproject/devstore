import OrderHistory from './OrderHistory';
import RecentlyViewList from './RecentlyViewList';

export default function OrderList() {
  return (
    <div className="flex flex-col">
      <RecentlyViewList />
      <OrderHistory />
    </div>
  );
}
