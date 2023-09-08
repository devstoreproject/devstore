import type { Order } from 'model/order';
import addCommasToPrice from 'utils/addCommasToPrice';
import addPeriodToDate from 'utils/admin/order/addPeriodToDate';
import descriptionToOrderStatus from 'utils/admin/order/descriptionToOrderStatus';
import orderCount from 'utils/admin/order/orderCount';

export default function OrderListTableContents({
  orderNumber,
  ordersStatus,
  createdAt,
  orderItemList,
  totalPrice,
}: Order) {
  const descOrderStatus = descriptionToOrderStatus(ordersStatus);
  const date = addPeriodToDate(createdAt);
  const price = addCommasToPrice(totalPrice);
  const totalOrderCount = orderCount(orderItemList);

  return (
    <div className="flex justify-between mb-2">
      <span>1</span>
      <span className="w-48 text-center">{orderNumber}</span>
      <span className="w-20 text-center">{descOrderStatus}</span>
      <span className="w-40 text-center">{date}</span>
      <p className="text-center truncate w-60">
        {orderItemList[0]?.itemName} 외 {totalOrderCount}
      </p>
      <span className="w-40 text-center">{price}</span>
    </div>
  );
}
