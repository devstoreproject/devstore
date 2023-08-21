import { useState } from 'react';

interface CartItemOptType {
  isOptOpen: boolean;
  isOptName: string;
  setIsOptName: React.Dispatch<React.SetStateAction<string>>;
  fetchOptChange: (optionId: number, itemCount: number) => void;
  itemCount: number;
  isOpt: isOptType[];
}

interface isOptType {
  additionalPrice: number;
  itemCount: number;
  itemId: number;
  optionDetail: string;
  optionId: number;
  optionName: string;
}

function CartItemOpt({
  isOptOpen,
  isOptName,
  setIsOptName,
  fetchOptChange,
  itemCount,
  isOpt,
}: CartItemOptType) {
  const [isOptDetail, setIsOptDetail] = useState('옵션 선택');
  const [isOptId, setIsOptId] = useState<number>(-1);
  const [isFoldName, setIsFoldName] = useState(false);
  const [isFoldDetail, setIsFoldDetail] = useState(false);
  const handleFoldName = () => {
    setIsFoldName(!isFoldName);
    setIsFoldDetail(false);
  };
  const handleFoldDetail = () => {
    setIsFoldDetail(!isFoldDetail);
    setIsFoldName(false);
  };
  // 옵션 이름만 추출해서 중복없이 배열로 만들기
  // 만든 배열을 map으로 옵션 목록으로 만들어주기
  const filteredOpt = () => {
    const optFilter = (opt: isOptType) => {
      // return opt.optionName === isOptName;
      return true;
    };
    if (isOpt?.length !== 0) {
      const result = isOpt.filter(optFilter);
      return result;
    }
  };
  const filteredOptArr = filteredOpt();

  return (
    <div
      className={`p-5 bg-white border-gray-300 rounded-xl border mt-6 absolute w-full z-10
            ${isOptOpen ? 'mb-10' : 'hidden'}`}
    >
      <div className="relative h-14 w-full bg-white border-gray-300 mb-5 last-of-type:mb-0">
        <ul
          className={`top-0 left-0 border border-gray-300 rounded-full px-10 bg-light-gray w-full absolute z-30 ${
            isFoldName ? 'rounded-xl' : 'overflow-hidden rounded-full h-14'
          }`}
          onClick={handleFoldName}
        >
          <li className="h-14 flex items-center z-30">{isOptName}</li>
          <li
            className="h-14 flex items-center z-30"
            onClick={() => {
              setIsOptName('옵션 목록1');
            }}
          >
            옵션 목록1
          </li>
        </ul>
      </div>
      {isOptName !== '옵션 목록' ? (
        <div className="relative h-14 w-full bg-white border-gray-300 mb-5 last-of-type:mb-0">
          <ul
            className={`top-0 left-0 border border-gray-300 rounded-full px-10 bg-light-gray w-full absolute z-20 ${
              isFoldDetail ? 'rounded-xl' : 'overflow-hidden rounded-full h-14'
            }`}
            onClick={handleFoldDetail}
          >
            <li className="h-14 flex items-center">{isOptDetail}</li>
            {filteredOptArr?.map((opt) => (
              <li
                key={opt.optionId}
                className="h-14 flex items-center"
                onClick={() => {
                  setIsOptDetail(opt.optionDetail);
                  setIsOptId(opt.optionId);
                }}
              >
                옵션 1
              </li>
            ))}
          </ul>
        </div>
      ) : null}
      <button
        className="absolute -bottom-10 right-0 px-4 h-7 rounded-full border ml-6 border-light-black hover:text-white hover:bg-light-black"
        onClick={() => {
          if (isOptId !== -1) {
            fetchOptChange(1, itemCount);
          }
        }}
      >
        변경하기
      </button>
    </div>
  );
}

export default CartItemOpt;
