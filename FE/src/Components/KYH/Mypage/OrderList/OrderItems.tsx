import OrderItem from './OrderItem';
import useFetchOrders from 'hooks/mypage/useFetchOrders';

export default function OrderItems() {
  const orders = useFetchOrders();

  return (
    <div className="flex flex-col">
      <span className="mb-4 font-bold">주문 목록</span>
      <ul className="w-200">
        {orders.map((order) => (
          <OrderItem
            key={order.orderId}
            orderNumber={order.orderNumber}
            discountedPrice={order.discountedPrice}
            createdAt={order.createdAt}
            orderItemList={order.orderItemList}
            ordersStatus={order.ordersStatus}
          />
        ))}
      </ul>
    </div>
  );
}
