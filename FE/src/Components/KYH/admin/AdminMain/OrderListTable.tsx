import useFetchOrders from 'hooks/mypage/useFetchOrders';
import OrderListTableContents from './OrderListTableContents';
import OrderListTableTitle from './OrderListTableTitle';

export default function OrderListTable() {
  const orders = useFetchOrders();

  return (
    <>
      <OrderListTableTitle />
      {orders.length === 0 ? (
        <span className="flex justify-center items-center w-344 h-132.8">
          주문된 상품 내역이 없습니다.
        </span>
      ) : (
        orders.map((order, idx) => (
          <OrderListTableContents
            key={order.orderId}
            idx={idx}
            orderNumber={order.orderNumber}
            ordersStatus={order.ordersStatus}
            createdAt={order.createdAt}
            orderItemList={order.orderItemList}
            totalPrice={order.totalPrice}
          />
        ))
      )}
    </>
  );
}
