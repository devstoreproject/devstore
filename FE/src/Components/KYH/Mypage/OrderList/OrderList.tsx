import RecentlyViewList from './RecentlyViewList';
import Orders from './Orders';

export default function OrderList() {
  return (
    <div className="flex flex-col">
      <RecentlyViewList />
      <Orders />
    </div>
  );
}
