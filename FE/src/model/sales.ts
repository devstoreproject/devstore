export interface Sales {
  localDate: string;
  totalOriginalPrice: number;
}

export interface ItemSales {
  itemId?: number;
  itemName: string;
  itemPrice: number;
}
