import api from 'api';
import { useState } from 'react';

interface CartItemOptType {
  isOptOpen: boolean;
  isOptName: string;
  setIsOptName: React.Dispatch<React.SetStateAction<string>>;
  fetchOptChange: (optionId: number, itemCount: number) => void;
  fetchDelCart: (itemId: number) => void;
  itemId: number;
  itemCount: number;
  inCartId: number[];
  isOpt: isOptType[];
  setIsCart: React.Dispatch<React.SetStateAction<any>>;
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
  fetchDelCart,
  itemCount,
  inCartId,
  itemId,
  isOpt,
  setIsCart,
}: CartItemOptType) {
  const [isOptDetail, setIsOptDetail] = useState('옵션 선택');
  const [isOptId, setIsOptId] = useState<number>(-1);
  const [isFoldName, setIsFoldName] = useState(false);
  const [isFoldDetail, setIsFoldDetail] = useState(false);
  const handleFoldName = () => {
    setIsFoldName(!isFoldName);
    setIsFoldDetail(false);
    setIsOptDetail('옵션 선택');
    setIsOptId(-1);
  };
  const handleFoldDetail = () => {
    setIsFoldDetail(!isFoldDetail);
    setIsFoldName(false);
  };
  // 옵션 이름만 추출해서 중복없이 배열로 만드는 함수
  const optNameListUp = () => {
    if (isOpt !== undefined && isOptOpen) {
      const listArr = isOpt.map((opt) => {
        if (opt.optionName !== null) return opt.optionName;
        else return '빈 옵션';
      });
      const optNameResult: string[] = [];
      for (const opt of listArr) {
        if (!optNameResult.includes(opt) && opt !== '빈 옵션') {
          optNameResult.push(opt);
        }
      }
      if (optNameResult.length === 0) optNameResult.push('옵션이 없습니다');
      return optNameResult;
    }
  };
  const optNameList = optNameListUp();
  const filteredOpt = () => {
    const optFilter = (opt: isOptType) => {
      return opt.optionName === isOptName;
    };
    if (isOpt?.length !== 0) {
      const result = isOpt.filter(optFilter);
      return result;
    }
  };
  const filteredOptArr = filteredOpt();

  const changeOpt = (e: React.MouseEvent<HTMLElement>) => {
    const data = {
      itemList: [
        {
          optionId: isOptId,
          itemCount: itemCount,
        },
      ],
    };
    const userId = Number(localStorage.getItem('userId'));
    const delNum = {
      deleteIdList: [itemId],
    };
    if (inCartId.includes(isOptId)) {
      // api
      //   .patch(`api/cart/users/${Number(localStorage.getItem('userId'))}`, data)
      //   .then(() => {
      //     fetchDelCart(itemId);
      //   })
      //   .catch((err) => {
      //     console.log(err);
      //   });
    } else {
      api
        .patch(`api/cart/users/${userId}/del`, delNum)
        .then(() => {
          api
            .post(`api/cart/users/${userId}`, data)
            .then(() => {
              api
                .get(`api/cart/users/${userId}`)
                .then((res) => {
                  const cartItems = res.data.data.itemList;
                  setIsCart(cartItems);
                })
                .catch((err) => {
                  if (err.code === 'ERR_BAD_REQUEST') {
                    setIsCart([]);
                  }
                });
            })
            .catch((err) => {
              console.log(err);
            });
        })
        .catch((err) => {
          console.log(err);
        });
      // api
      //   .post(`api/cart/users/${Number(localStorage.getItem('userId'))}`, data)
      //   .then(() => {})
      //   .catch((err) => {
      //     console.log(err);
      //   });
    }
  };
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
          {optNameList?.map((opt) => (
            <li
              key={opt}
              className="h-14 flex items-center z-30"
              onClick={() => {
                setIsOptName(opt);
              }}
            >
              {opt}
            </li>
          ))}
        </ul>
      </div>
      {isOptName !== '옵션 목록' && isOptName !== '옵션이 없습니다' ? (
        <div className="relative h-14 w-full bg-white border-gray-300 mb-5 last-of-type:mb-0">
          <ul
            className={`top-0 left-0 border border-gray-300 rounded-full px-10 bg-light-gray w-full absolute z-20 ${
              isFoldDetail ? 'rounded-xl' : 'overflow-hidden rounded-full h-14'
            }`}
            onClick={handleFoldDetail}
          >
            <li className="h-14 flex items-center">{isOptDetail}</li>
            {filteredOptArr?.map((opt) =>
              opt.optionDetail !== null ? (
                <li
                  key={opt.optionId}
                  className="h-14 flex items-center"
                  onClick={() => {
                    setIsOptDetail(opt.optionDetail);
                    setIsOptId(opt.optionId);
                  }}
                >
                  {opt.optionDetail}
                </li>
              ) : null
            )}
          </ul>
        </div>
      ) : null}
      {isOptName !== '옵션 목록' && isOptName !== '옵션이 없습니다' && (
        <button
          className="absolute -bottom-10 right-0 px-4 h-7 rounded-full border ml-6 border-light-black hover:text-white hover:bg-light-black"
          onClick={(e) => {
            if (isOptId !== -1) {
              changeOpt(e);
              fetchDelCart(itemId);
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
