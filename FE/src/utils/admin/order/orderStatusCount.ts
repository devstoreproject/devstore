import useFetchOrders from 'hooks/mypage/useFetchOrders';

const orderStatusCount = (status: string) => {
  const orders = useFetchOrders();
  const count = orders.filter((order) => order.ordersStatus === status).length;

  return count;
};

export default orderStatusCount;
