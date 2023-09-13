import InquiryList from './InquiryList';
import OrderList from './OrderList';
import OrderStatus from './OrderStatusList';
import SalesChart from './SalesChart';

export default function AdminMain() {
  return (
    <div className="flex flex-col mr-10">
      <div className="flex">
        <SalesChart />
        <div className="flex flex-col ml-6">
          <OrderStatus />
          <InquiryList />
        </div>
      </div>
      <OrderList />
    </div>
  );
}
