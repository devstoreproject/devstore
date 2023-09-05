export interface Product {
  itemId: number;
  name: string;
  itemPrice: number;
  totalCount: number;
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
