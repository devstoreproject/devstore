export interface Product {
  itemId: number;
  name: string;
  itemPrice: number;
  totalCount: number;
  optionList: Option[];
  salesQuantity: number;
}

export interface ProductDetail {
  name: string;
  category: string;
  itemPrice: number;
  totalCount: number;
  optionList: Option[];
  salesQuantity: 0;
}

export interface Option {
  itemId?: number;
  optionId?: number;
  itemCount: number;
  additionalPrice: number;
  optionName: string | null;
  optionDetail: string | null;
}

export interface Bookmark {
  itemId: number;
  name: string;
  like: boolean;
  itemPrice: number;
  imageList: image[];
}

interface image {
  imageId: number;
  thumbnailPath: string;
}
