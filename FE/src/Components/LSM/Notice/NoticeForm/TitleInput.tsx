import { useState } from 'react';
interface NoticeProp {
  datas: any;
  path: string;
  setTitle: React.Dispatch<React.SetStateAction<string>>;
  setEditTitle: React.Dispatch<React.SetStateAction<string>>;
}

export default function TitleInput({
  setTitle,
  path,
  datas,
  setEditTitle,
}: NoticeProp) {
  const [inputCount, setInputCount] = useState('0');
  const inputHandler = (e: any) => {
    if (path === 'post') {
      setTitle(e.target.value);
    }
    if (path !== 'post') {
      setEditTitle(e.target.value);
    }
    if (e.target.value.length > 40) {
      e.target.value = e.target.value.slice(0, 40);
    }
    setInputCount(e.target.value.length);
  };
  const maxLength = 40;
  return (
    <>
      {path === 'post' ? (
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
      ) : (
        <div className="flex items-center justify-between w-full">
          <input
            type="text"
            placeholder="제목을 입력해 주세요"
            className="flex-1 px-2 py-1 ml-2 border rounded-lg border-gray"
            onChange={inputHandler}
            maxLength={maxLength}
            defaultValue={datas.title}
          />
          <div className="w-16 text-xs text-right flex-3">
            <span className="text-subtitle-gray">{inputCount.toString()}</span>
            <span className="text-subtitle-gray">
              {` / ${maxLength.toString()}자`}
            </span>
          </div>
        </div>
      )}
    </>
  );
}
