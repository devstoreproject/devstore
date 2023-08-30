export interface CartItemType {
  cartId: number;
  userId: number;
  deliveryPrice: number;
  totalPrice: number;
  discountedPrice: number;
  itemList: CartItemList[];
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
