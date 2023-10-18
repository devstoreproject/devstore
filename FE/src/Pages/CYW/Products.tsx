import CategoryResult from 'Components/CYW/SearchCategory/CategoryResult';
import api from 'api';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

export interface CategoryOptionList {
  itemId: number;
  optionId: number;
  optionDetail: string | null;
  additionPrice: number;
  itemCount: number;
}

export interface CategorySpecList {
  specId: number;
  itemName: string;
  content: string;
}

export interface ImageListType {
  imageId: number;
  imageOrder: number;
  originalPath: string;
  representative: boolean;
  thumbnailPath: string;
  title: string;
}

export interface CategoryContent {
  category: string;
  defaultCount: number;
  deliveryPrice: number;
  description: string;
  imageList: ImageListType[];
  itemId: number;
  itemPrice: number;
  name: string;
  optionList: CategoryOptionList[];
  specList: CategorySpecList[];
  totalCount: number;
  viewCount: number;
}

export interface CategorySort {
  empty: boolean;
  sorted: boolean;
  unsorted: boolean;
}
export interface CategoryPageable {
  offset: number;
  pageNumber: number;
  pagesize: number;
  paged: boolean;
  sort: CategorySort;
  unpaged: boolean;
}
export interface CategoryType {
  content: CategoryContent[];
  empty: boolean;
  first: boolean;
  last: boolean;
  number: number;
  numberOfElements: number;
  pageable: CategoryPageable;
  size: number;
  sort: CategorySort;
  totalElements: number;
  totalPages: number;
}

export default function Products() {
  const { category } = useParams();
  const [categoryData, setCategoryData] = useState<CategoryContent[]>([]);
  const categoryName = category === undefined ? '전체' : category;
  const bigCategory: string | undefined = category?.toLocaleUpperCase();

  useEffect(() => {
    const apiEndPoint = `api/items/search/category?category=${
      bigCategory as string
    }&page=0&size=20&sort=createdAt`;

    api
      .get(apiEndPoint)
      .then((res) => {
        setCategoryData(res.data.data.content);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [setCategoryData, bigCategory]);

  return (
    <div className="flex flex-col pt-10">
      <p className="text-slate-600 pl-136">{`홈 > 카테고리 > ${categoryName}`}</p>
      <div className="flex flex-col items-center">
        <h1 className="flex justify-center text-3xl font-bold pt-10">
          {categoryName}
        </h1>
        <CategoryResult categoryData={categoryData} />
      </div>
    </div>
  );
}
