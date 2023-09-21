import type { SearchType } from 'Pages/CYW/Search';
import api from 'api';
import { useState } from 'react';

interface OwnProps {
  option: string;
  setOption: React.Dispatch<React.SetStateAction<string>>;
  search: SearchType[] | null;
  setSearch: React.Dispatch<React.SetStateAction<SearchType[] | null>>;
}

export default function SearchTool({
  option,
  setOption,
  search,
  setSearch,
}: OwnProps) {
  const [input, setInput] = useState<string>('');

  const searchSubmitHandler = (e: React.FormEvent) => {
    e.preventDefault();

    api
      .get(`api/items/search/itemName?itemName=${input}&page=0&size=10`)
      .then((res) => {
        setSearch(res.data.data.content);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return (
    <form
      className="flex justify-center pt-5 w-full"
      onSubmit={searchSubmitHandler}
    >
      <select
        onChange={(e) => {
          setOption(e.currentTarget.value);
        }}
        value={option}
        className="rounded-l-full px-4 border"
      >
        <option value={'상품명'}>상품명</option>
        {/* <option value={'스펙'}></option> */}
      </select>
      <input
        id="searchTitle"
        onChange={(e) => {
          setInput(e.target.value);
        }}
        value={input}
        type="text"
        placeholder="찾고 싶은 상품을 검색해주세요."
        className="px-5 py-3 border-t border-r border-b w-1/3 rounded-r-full border-gray"
      />
      <button className="px-6 py-3 border border-gray bg-slate-800 text-slate-50 rounded-3xl ml-2">
        검색하기
      </button>
    </form>
  );
}
