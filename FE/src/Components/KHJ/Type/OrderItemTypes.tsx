export interface orderListType {
  data: {
    orderId: number;
    orderNumber: string;
    addressInfo: OrderAddress;
    userInfo: OrderUserInfo;
    orderItemList: orderItemType[];
    totalPrice: number;
    discountedPrice: number;
    deliveryPrice: number;
    ordersStatus: string;
    message: string;
    createdAt: number[];
    modifiedAt: number[];
  };
}

export interface orderItemType {
  itemId: number;
  optionId?: number;
  optionContent?: string;
  itemName: string;
  itemCount: number;
  itemPrice: number;
  discountPrice: number;
  discountRate: number;
}

export interface OrderAddress {
  recipient: string;
  zipCode: string;
  addressSimple: string;
  addressDetail: string;
  phone: string;
}
export interface OrderUserInfo {
  id: number;
  nickName: string;
  profileImage: null;
  email: string;
}
