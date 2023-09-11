import SearchTool from 'Components/CYW/Search/SearchTool';
import SearchList from 'Components/CYW/Search/SearchList';
import { useState } from 'react';

export interface OptionListType {
  additionalPrice: number;
  itemCount: number;
  itemId: number;
  optionDetail: null;
  optionId: number;
  optionName: string;
}
export interface SpecListType {
  content: string;
  specId: number;
  specName: string;
}
export interface ImageListType {
  imageId: number;
  imageOrder: number;
  originalPath: string;
  representative: boolean;
  thumbnailPath: string;
  title: string;
}
export interface SearchType {
  category: string;
  defaultCount: number;
  deliveryPrice: number;
  description: string;
  imageList: ImageListType[];
  itemId: number;
  itemPrice: number;
  like: false;
  name: string;
  optionList: OptionListType[];
  specList: SpecListType[];
  totalCount: number;
  viewCount: number;
}

export default function Search() {
  const [option, setOption] = useState('상품명');
  const [search, setSearch] = useState<SearchType[] | null>(null);

  return (
    <div className="flex flex-col pt-10">
      <div className="flex justify-center">
        <p className="flex text-slate-600 w-1/2">{`카테고리 > 검색`}</p>
      </div>
      <div className="flex flex-col items-center">
        <div className="flex justify-center w-full">
          <SearchTool
            option={option}
            setOption={setOption}
            search={search}
            setSearch={setSearch}
          />
        </div>
        <SearchList search={search} />
      </div>
    </div>
  );
}
