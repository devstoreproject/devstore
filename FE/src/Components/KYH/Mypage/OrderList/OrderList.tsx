import RecentlyViewList from './RecentlyViewList';
import OrderItems from './OrderItems';

export default function OrderList() {
  return (
    <div className="flex flex-col">
      <RecentlyViewList />
      <OrderItems />
    </div>
  );
}
