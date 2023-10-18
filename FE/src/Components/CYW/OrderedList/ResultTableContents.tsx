import type { Order } from 'model/order';
import { useState } from 'react';
import addCommasToPrice from 'utils/addCommasToPrice';
import addPeriodToDate from 'utils/admin/order/addPeriodToDate';
import descriptionToOrderStatus from 'utils/admin/order/descriptionToOrderStatus';
import orderCount from 'utils/admin/order/orderCount';
import ResultTableContentsDetail from './ResultTableContentsDetail';

interface OwnProps extends Order {
  commonStyle: string;
  ordersLength: number;
  idx: number;
}

export default function ResultTableContents({
  commonStyle,
  idx,
  orderNumber,
  ordersStatus,
  createdAt,
  orderItemList,
  totalPrice,
  ordersLength,
}: OwnProps) {
  const [isAccordionOpen, setIsAccordionOpen] = useState(false);
  const status = descriptionToOrderStatus(ordersStatus);
  const date = addPeriodToDate(createdAt);
  const totalOrderCount = orderCount(orderItemList);
  const price = addCommasToPrice(totalPrice);

  const AccordionOpenBtnHandler = () => {
    setIsAccordionOpen((prev) => !prev);
  };

  return (
    <button
      className={`flex flex-col hover:bg-gray-300 ${
        ordersLength - 1 === idx ? '' : 'border-b border-b-gray-400'
      } ${isAccordionOpen ? 'pt-4 justify-start' : 'h-14 justify-center'}`}
      onClick={AccordionOpenBtnHandler}
    >
      <div className="flex">
        <span className={`${commonStyle} w-16`}>{idx + 1}</span>
        <span className={`${commonStyle} w-72`}>{orderNumber}</span>
        <span className={`${commonStyle} w-40`}>{status}</span>
        <span className={`${commonStyle} w-52`}>{date}</span>
        <span className={`${commonStyle} w-80`}>
          {orderItemList[0]?.itemName} 외 {totalOrderCount}
        </span>
        <span className={`${commonStyle} w-40`}>{price}</span>
      </div>
      {isAccordionOpen ? (
        <ResultTableContentsDetail orderItemList={orderItemList} />
      ) : null}
    </button>
  );
}
