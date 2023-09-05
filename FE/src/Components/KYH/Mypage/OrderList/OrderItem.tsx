import type { Order } from 'model/order';
import { useState } from 'react';
import addCommasToPrice from 'utils/addCommasToPrice';
import OrderItemDetail from './OrderItemDetail';
import OrderItemDetailTitle from './OrderItemDetailTitle';
import addPeriodToDate from 'utils/admin/order/addPeriodToDate';
import orderCount from 'utils/admin/order/orderCount';
import descriptionToOrderStatus from 'utils/admin/order/descriptionToOrderStatus';

export default function OrderItem({
  orderNumber,
  discountedPrice,
  createdAt,
  orderItemList,
  ordersStatus,
}: Order) {
  const [isAccordionOpen, setIsAccordionOpen] = useState(false);
  const totalPrice = addCommasToPrice(discountedPrice);
  const date = addPeriodToDate(createdAt);
  const totalOrderCount = orderCount(orderItemList);
  const descOrderStatus = descriptionToOrderStatus(ordersStatus);

  return (
    <li>
      <label
        className={`flex flex-col px-4 pt-4 pb-4 mb-1 bg-white border cursor-pointer border-gray-300 rounded-xl hover:bg-gray-100 hover:border-gray-400 transition-all duration-200 ease-linear ${
          isAccordionOpen ? '' : 'h-20'
        }`}
      >
        <input
          type="button"
          className="hidden"
          onClick={() => {
            setIsAccordionOpen((prev) => !prev);
          }}
        />
        <div className="flex pb-2 border-b border-b-gray-300">
          <div className="flex flex-col">
            <span className="text-sm">주문번호</span>
            <span className="mt-2 text-sm text-gray-500">{orderNumber}</span>
          </div>
          <div className="flex flex-col ml-16 w-72">
            <p className="text-sm">
              {orderItemList[0]?.itemName} 외 {totalOrderCount}
            </p>
            <span className="mt-2 text-sm text-gray-500">
              총 {totalPrice}원
            </span>
          </div>
          <div className="flex flex-col w-72">
            <span className="text-sm text-gray-500 text-end">
              주문일자 : {date}
            </span>
            <span className="font-bold text-end">{descOrderStatus}</span>
          </div>
        </div>
        {isAccordionOpen ? <OrderItemDetailTitle /> : null}
        {isAccordionOpen
          ? orderItemList.map((orderItem, idx) => (
              <OrderItemDetail
                key={idx}
                idx={idx}
                itemPrice={orderItem.itemPrice}
                itemName={orderItem.itemName}
                itemCount={orderItem.itemCount}
                discountPrice={orderItem.discountPrice}
                itemId={orderItem.itemId}
              />
            ))
          : null}
      </label>
    </li>
  );
}
