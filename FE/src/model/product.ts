export interface Product {
  itemId: number;
  name: string;
  itemPrice: number;
  optionList: option[];
}

export interface option {
  itemCount: number;
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
