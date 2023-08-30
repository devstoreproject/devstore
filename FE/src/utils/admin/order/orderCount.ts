import type { OrderItem } from 'model/order';

const orderCount = (orderItemList: OrderItem[]) => {
  return orderItemList.length === 1 ? '' : `${orderItemList.length - 1}건`;
};

export default orderCount;
