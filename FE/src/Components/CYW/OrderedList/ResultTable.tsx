import type { Order } from 'model/order';
import ResultTableContents from './ResultTableContents';
import ResultTableTitle from './ResultTableTitle';

interface OwnProps {
  orders: Order[];
}

export default function ResultTable({ orders }: OwnProps) {
  return (
    <div className="flex flex-col mt-2 mb-4 bg-gray-100 border border-gray-400 rounded-lg w-300 h-132.8">
      <ResultTableTitle commonStyle={commonStyle} />
      {orders.length === 0 ? (
        <span className="flex justify-center items-center w-300 h-132.8">
          주문된 상품 내역이 없습니다.
        </span>
      ) : (
        orders.map((order, idx) => (
          <ResultTableContents
            key={order.orderId}
            idx={idx}
            ordersLength={orders.length}
            commonStyle={commonStyle}
            orderNumber={order.orderNumber}
            ordersStatus={order.ordersStatus}
            createdAt={order.createdAt}
            orderItemList={order.orderItemList}
            totalPrice={order.totalPrice}
          />
        ))
      )}
    </div>
  );
}

const commonStyle = 'flex items-center justify-center';
