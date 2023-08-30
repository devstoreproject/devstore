import useFetchOrders from 'hooks/mypage/useFetchOrders';
import OrderListTableContents from './OrderListTableContents';
import OrderListTableTitle from './OrderListTableTitle';

export default function OrderListTable() {
  const orders = useFetchOrders();

  return (
    <>
      <OrderListTableTitle />
      {orders.map((order) => (
        <OrderListTableContents
          key={order.orderId}
          orderNumber={order.orderNumber}
          ordersStatus={order.ordersStatus}
          createdAt={order.createdAt}
          orderItemList={order.orderItemList}
          discountedPrice={order.discountedPrice}
        />
      ))}
    </>
  );
}
