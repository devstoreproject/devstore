import type { Order } from 'model/order';
import { useState } from 'react';
import addCommasToPrice from 'utils/addCommasToPrice';
import OrderItemDetail from './OrderItemDetail';

type OwnProps = Omit<Order, 'orderId'>;

export default function OrderItem({
  orderNumber,
  discountedPrice,
  createdAt,
  orderItemList,
}: OwnProps) {
  const [isAccordionOpen, setIsAccordionOpen] = useState(false);
  const totalPrice = addCommasToPrice(discountedPrice);
  const totalOrderCount =
    orderItemList.length === 1 ? '' : `${orderItemList.length - 1}건`;

  return (
    <li>
      <label
        className={`flex flex-col px-4 pt-4 pb-4 mb-1 bg-white border-b cursor-pointer border-b-gray-300 rounded-xl hover:bg-gray-200 ${
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
          <div className="flex flex-col ml-16 w-128">
            <p className="text-sm">
              {orderItemList[0].itemName} 외 {totalOrderCount}
            </p>
            <span className="mt-2 text-sm text-gray-500">
              총 {totalPrice}원
            </span>
          </div>
          <div className="flex flex-col w-24">
            <span className="text-sm text-right">주문일자</span>
            <span className="mt-2 text-sm text-right text-gray-500">
              {createdAt.slice(0, 3).join('. ')}
            </span>
          </div>
        </div>
        {isAccordionOpen
          ? orderItemList.map((orderItem, idx) => (
              <OrderItemDetail
                key={idx}
                idx={idx}
                itemPrice={orderItem.itemPrice}
                itemName={orderItem.itemName}
                itemCount={orderItem.itemCount}
                discountPrice={orderItem.discountPrice}
              />
            ))
          : null}
      </label>
    </li>
  );
}
