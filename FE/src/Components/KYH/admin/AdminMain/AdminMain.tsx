import InquiryList from './InquiryList';
import OrderList from './OrderList';
import OrderStatus from './OrderStatus';
import SalesChart from './SalesChart';

export default function AdminMain() {
  return (
    <div className="flex flex-col mr-10">
      <div className="flex">
        <SalesChart />
        <div className="flex flex-col w-1/2 ml-6">
          <OrderStatus />
          <InquiryList />
        </div>
      </div>
      <OrderList />
    </div>
  );
}