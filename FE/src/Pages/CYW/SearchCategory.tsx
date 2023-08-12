import CategoryResult from 'Components/CYW/SearchCategory/CategoryResult';
import api from 'api';
import { useEffect, useState } from 'react';

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

export interface CategoryContent {
  category: string;
  defaultCount: number;
  deliveryPrice: number;
  description: string;
  imageList: [];
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

export default function SearchCategory() {
  const [category, setCategory] = useState<CategoryContent[] | null>(null);

  useEffect(() => {
    api
      .get('api/items?page=0&size=10')
      .then((res) => {
        setCategory(res.data.data.content);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [setCategory]);

  return (
    <div className="flex flex-col pt-10">
      <p className="text-slate-600 pl-136">{`홈 > 카테고리 > 전체`}</p>
      <div className="flex flex-col items-center">
        <h1 className="flex justify-center text-3xl font-bold pt-10">전체</h1>
        <CategoryResult category={category} />
      </div>
    </div>
  );
}
