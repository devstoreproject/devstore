export interface Order {
  orderId?: number;
  orderNumber: string;
  totalPrice: number;
  createdAt: number[];
  ordersStatus:
    | 'ORDER_COMPLETE'
    | 'ORDER_CANCEL'
    | 'PAYMENT_PROGRESS'
    | 'PAYMENT_COMPLETE'
    | 'PAYMENT_CANCEL'
    | 'DELIVERY_PROGRESS'
    | 'DELIVERY_COMPLETE'
    | 'REFUND_PROGRESS'
    | 'REFUND_COMPLETE';
  orderItemList: OrderItem[];
}

export interface OrderItem {
  itemId?: number;
  itemName: string;
  itemCount: number;
  itemPrice: number;
}
