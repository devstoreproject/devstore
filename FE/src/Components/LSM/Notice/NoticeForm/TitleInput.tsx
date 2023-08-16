import { useState } from 'react';
interface NoticeProp {
  setTitle: any;
  setEditTitle: any;
}

export default function TitleInput({ setTitle }: NoticeProp) {
  const [inputCount, setInputCount] = useState('0');
  const inputHandler = (e: any) => {
    if (e.target.value.length > 40) {
      e.target.value = e.target.value.slice(0, 40);
    }
    setTitle(e.target.value);
    setInputCount(e.target.value.length);
  };
  const maxLength = 40;
  return (
    <div className="flex items-center justify-between w-full">
      <input
        type="text"
        placeholder="제목을 입력해 주세요"
        className="flex-1 px-2 py-1 ml-2 border rounded-lg border-gray"
        onChange={inputHandler}
        maxLength={maxLength}
      />
      <div className="w-16 text-xs text-right flex-3">
        <span className="text-subtitle-gray">{inputCount.toString()}</span>
        <span className="text-subtitle-gray">
          {` / ${maxLength.toString()}자`}
        </span>
      </div>
    </div>
  );
}
