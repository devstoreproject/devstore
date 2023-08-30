import { useState } from 'react';
import type { CartItemList } from 'Components/KHJ/Type/CartTypes';
import api from 'api';
import CartItemOpt from './CartItemOpt';

interface CartItemProps {
  item: CartItemList;
  userId: number;
  getCart: (userId: number) => void;
}

interface itemBody {
  optionId: number;
  itemCount: number;
}

function CartItem({
  item,
  userId,
  getCart,
}: CartItemProps): React.ReactElement {
  const [isOptOpen, setIsOptOpen] = useState(false);
  const [isQuantity, setIsQuantity] = useState(item.count);
  const [isOptName, setIsOptName] = useState('옵션 목록');
  const [isOpt, setIsOpt] = useState([]);
  // 옵션 변경창 열기
  const handleOptOpen = () => {
    setIsOptOpen(!isOptOpen);
    setIsOptName('옵션 목록');
  };

  // 가격에 콤마 찍어주기
  const regexComma = /\B(?=(\d{3})+(?!\d))/g;
  const priceComma = item.defaultPrice.toString().replace(regexComma, ',');
  const discount = item.defaultPrice * item.discountRate;
  const discountPrice = item.defaultPrice - discount;
  const discountPriceComma = discountPrice.toString().replace(regexComma, ',');

  // 장바구니 변겅 API
  const fetchCartCount = (userId: number, itemBody: itemBody) => {
    const item = {
      itemList: [itemBody],
    };
    api.patch(`api/cart/users/${userId}`, item).catch((err) => {
      console.log(err);
    });
  };

  // 장바구니 삭제 API
  const fetchDelCart = (itemId: number) => {
    const dataBody = {
      deleteIdList: [itemId],
    };
    api
      .patch(`api/cart/users/${userId}/del`, dataBody)
      .then(() => {
        const userId = Number(localStorage.getItem('userId'));
        getCart(userId);
        setIsOptOpen(false);
        setIsOptName('옵션 목록');
      })
      .catch((err) => {
        console.log(err);
      });
  };

  // 수량 다운
  const handleQuantityDown = () => {
    // count 1일 경우, 다운키를 눌렀을 때 알람발생, 예를 누르면 장바구니에서 삭제
    if (isQuantity === 1) {
      const answer = confirm('장바구니에서 삭제하시겠습니까?');
      if (answer) {
        fetchDelCart(item.optionId);
      }
    }
    if (isQuantity > 1) {
      const patchBody = {
        optionId: item.optionId,
        itemCount: isQuantity - 1,
      };
      fetchCartCount(userId, patchBody);
      setIsQuantity(isQuantity - 1);
    }
  };

  // 수량 업
  const handleQuantityUp = () => {
    const patchBody = {
      optionId: item.optionId,
      itemCount: isQuantity + 1,
    };
    fetchCartCount(userId, patchBody);
    setIsQuantity(isQuantity + 1);
  };

  // 옵션 데이터 가져오기
  const fetchGetOpt = async () => {
    await api
      .get(`api/items/${item.itemId}/options`)
      .then((res) => {
        const data = res.data.data;
        setIsOpt(data);
      })
      .catch((err) => {
        console.log(err);
      });
  };
  // 옵션 변경
  const fetchOptChange = (optionId: number, itemCount: number) => {
    const userId = Number(localStorage.getItem('userId'));
    const data = {
      itemList: [
        {
          optionId,
          itemCount,
        },
      ],
    };
    api
      .post(`api/cart/users/${userId}`, data)
      .then(() => {
        fetchDelCart(optionId);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return (
    <li
      className={`flex items-center justify-between ${
        isOptOpen ? 'mb-28' : 'mb-10 last-of-type:mb-0'
      }
      ${isOptName !== '옵션 목록' ? 'pb-20' : ''}`}
    >
      {/* <input type="checkbox" className="w-5 h-5 border-gray-300"></input> */}
      <div className="rounded-xl w-36 h-36 bg-white"></div>
      <div className="w-3/5 relative">
        <div className="flex items-center justify-between">
          <div>
            <h3>{item.itemName}</h3>
            <div className="text-subtitle-gray">
              {item.optionName !== null ? (
                <span className="mr-4">색상 : {item.optionName}</span>
              ) : null}
            </div>
          </div>
          <div className="flex">
            <button
              className="w-7 h-7 rounded-full border border-gray-300"
              onClick={handleQuantityDown}
            >
              -
            </button>
            <p className="mx-6">{isQuantity}</p>
            <button
              className="w-7 h-7 rounded-full border border-gray-300"
              onClick={handleQuantityUp}
            >
              +
            </button>
            {item.optionName !== null ? (
              <button
                className={`px-4 h-7 rounded-full border ml-6 ${
                  isOptOpen
                    ? 'border-light-black text-white bg-light-black'
                    : 'border-gray-300'
                }`}
                onClick={() => {
                  handleOptOpen();
                  if (!isOptOpen && isOpt.length === 0) {
                    void fetchGetOpt();
                  }
                }}
              >
                옵션변경
              </button>
            ) : null}
          </div>
        </div>
        {item.optionName !== null ? (
          <CartItemOpt
            isOptOpen={isOptOpen}
            isOptName={isOptName}
            setIsOptName={setIsOptName}
            fetchOptChange={fetchOptChange}
            itemCount={item.count}
            isOpt={isOpt}
          />
        ) : null}
      </div>
      <div className="relative">
        <span className="absolute bottom-3/4 text-subtitle-gray right-0">
          할인가
        </span>
        <del className="text-subtitle-gray mr-2">{priceComma}</del>
        <strong className="text-xl font-normal">{discountPriceComma}</strong>
      </div>
    </li>
  );
}

export default CartItem;
