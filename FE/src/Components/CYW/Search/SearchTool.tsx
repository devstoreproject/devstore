import { useState } from 'react';

export default function SearchTool() {
  const [option, setOption] = useState('상품명');
  const [search, setSearch] = useState('');
  const searchSubmitHandler = () => {
    // option, search
  };

  console.log(option, search);
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
        <option value={'상품명'}></option>
        <option value={'스펙'}></option>
      </select>
      <input
        id="searchTitle"
        onChange={(e) => {
          setSearch(e.target.value);
        }}
        value={search}
        type="text"
        placeholder="찾고 싶은 상품을 검색해주세요."
        className="px-5 py-3 border-t border-r border-b lg:w-full rounded-r-full border-gray w-1/3"
      />
      <button className="px-24 py-3 border border-gray bg-slate-800 text-slate-50 rounded-3xl ml-2">
        검색하기
      </button>
    </form>
  );
}
