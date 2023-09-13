import OrderStatus from './OrderStatus';

export default function OrderStatusList() {
  const statusList = [
    {
      status: 'ORDER_COMPLETE',
      statusName: '주문 완료',
      style: 'mr-2',
    },
    {
      status: 'PAYMENT_COMPLETE',
      statusName: '결제 완료',
      style: 'mr-2',
    },
    {
      status: 'DELIVERY_COMPLETE',
      statusName: '배송 완료',
      style: 'mr-2',
    },
    {
      status: 'REFUND_PROGRESS',
      statusName: '환불 진행중',
      style: 'mr-2',
    },
    {
      status: 'REFUND_COMPLETE',
      statusName: '환불 완료',
      style: '',
    },
  ];
  return (
    <div className="flex h-24 w-200">
      {statusList.map((orderStatus) => (
        <OrderStatus
          key={orderStatus.status}
          status={orderStatus.status}
          statusName={orderStatus.statusName}
          style={orderStatus.style}
        />
      ))}
    </div>
  );
}
