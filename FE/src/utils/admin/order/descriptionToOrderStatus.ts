type OrderStatus =
  | 'ORDER_COMPLETE'
  | 'ORDER_CANCEL'
  | 'PAYMENT_PROGRESS'
  | 'PAYMENT_COMPLETE'
  | 'PAYMENT_CANCEL'
  | 'DELIVERY_PROGRESS'
  | 'DELIVERY_COMPLETE'
  | 'REFUND_PROGRESS'
  | 'REFUND_COMPLETE';

const descriptionToOrderStatus = (status: OrderStatus) => {
  const orderStatus = {
    ORDER_COMPLETE: '주문 완료',
    ORDER_CANCEL: '주문 취소',
    PAYMENT_PROGRESS: '결제 진행중',
    PAYMENT_COMPLETE: '결제 완료',
    PAYMENT_CANCEL: '결제 취소',
    DELIVERY_PROGRESS: '배송중',
    DELIVERY_COMPLETE: '배송 완료',
    REFUND_PROGRESS: '환불 진행중',
    REFUND_COMPLETE: '환불 완료',
  };

  return orderStatus[status];
};

export default descriptionToOrderStatus;
