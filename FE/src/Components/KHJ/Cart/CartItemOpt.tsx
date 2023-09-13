// import api from 'api';
import api from 'api';
import { useState } from 'react';

interface CartItemOptType {
  isOptOpen: boolean;
  setIsOptName: React.Dispatch<React.SetStateAction<string>>;
  itemOptId: number;
  optName?: string;
  itemCount: number;
  isOpt: isOptType[];
  getCart: (userId: number) => void;
}

interface isOptType {
  additionalPrice: number;
  itemCount: number;
  itemOptId: number;
  optionDetail: string;
  optionId: number;
  optionName: string;
}

function CartItemOpt({
  isOptOpen,
  itemCount,
  optName,
  isOpt,
  itemOptId,
  getCart,
}: CartItemOptType) {
  const [isOptDetail, setIsOptDetail] = useState<string>('옵션 목록');
  const [isOptId, setIsOptId] = useState<number>(-1);
  const [isFoldDetail, setIsFoldDetail] = useState(false);
  const handleFoldDetail = () => {
    setIsFoldDetail(!isFoldDetail);
  };

  // 장바구니 변겅 API
  const fetchCartCount = (
    itemCount: number,
    itemDelCount: number,
    optionId: number
  ) => {
    const data = {
      patchItemList: [
        {
          optionId,
          itemCount,
        },
      ],
      deleteOptionId: [itemDelCount],
    };
    api
      .patch(`api/cart`, data)
      .then(() => {
        getCart(Number(localStorage.getItem('userId')));
      })
      .catch((err) => {
        console.log(err);
      });
  };

  // 옵션디테일이 있는 아이템만 추출해서 배열로 만드는 함수
  const optListUp = () => {
    const optFilter = (opt: isOptType) => {
      return opt.optionDetail !== null && opt.optionDetail !== optName;
    };
    if (isOpt?.length !== 0) {
      const result = isOpt.filter(optFilter);
      return result;
    }
  };
  const optDetailList = optListUp();

  return (
    <div
      className={`p-5 bg-white border-gray-300 rounded-xl border mt-6 absolute w-full z-10
            ${isOptOpen ? 'mb-10' : 'hidden'}`}
    >
      <div className="relative h-14 w-full bg-white border-gray-300 mb-5 last-of-type:mb-0">
        <ul
          className={`top-0 left-0 border border-gray-300 rounded-full px-10 bg-light-gray w-full absolute z-30 ${
            isFoldDetail ? 'rounded-xl' : 'overflow-hidden rounded-full h-14'
          }`}
          onClick={handleFoldDetail}
        >
          <li className="h-14 flex items-center z-30">{isOptDetail}</li>
          {optDetailList?.map((opt) => (
            <li
              key={opt.optionId}
              className="h-14 flex items-center z-30"
              onClick={() => {
                setIsOptDetail(opt.optionDetail);
                setIsOptId(opt.optionId);
              }}
            >
              {opt.optionDetail}
            </li>
          ))}
        </ul>
      </div>
      {isOptDetail !== '옵션 목록' && isOptDetail !== '옵션이 없습니다' && (
        <button
          className="absolute -bottom-10 right-0 px-4 h-7 rounded-full border ml-6 border-light-black hover:text-white hover:bg-light-black"
          onClick={() => {
            if (isOptId !== -1) {
              fetchCartCount(itemCount, itemOptId, isOptId);
            }
          }}
        >
          변경하기
        </button>
      )}
    </div>
  );
}

export default CartItemOpt;
