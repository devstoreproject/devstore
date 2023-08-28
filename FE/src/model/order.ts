export interface Order {
  orderId: number;
  orderNumber: string;
  discountedPrice: number;
  createdAt: number[];
  orderItemList: OrderItem[];
}

export interface OrderItem {
  itemName: string;
  itemCount: number;
  itemPrice: number;
  discountPrice: number;
}
