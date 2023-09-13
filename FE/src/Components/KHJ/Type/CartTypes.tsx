export interface cartInfoType {
  cartId: number;
  deliveryPrice: number;
  discountedPrice: number;
  itemList: CartItemList[];
  totalPrice: number;
  userId: number;
}

export interface CartItemList {
  itemId: number;
  optionId: number;
  count: number;
  defaultPrice: number;
  additionalPrice: number;
  discountRate: number;
  itemName: string;
  optionName?: string;
  optionDetail?: string;
  imageInfo: CartImage;
}

export interface CartImage {
  imageId: number;
  originalPath: string;
  thumbnailPath: string;
  title: string;
  imageOrder: number;
  representative: boolean;
}
