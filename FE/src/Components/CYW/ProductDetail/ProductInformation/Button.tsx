import type { OptionListType, ProductType } from 'Pages/CYW/ProductDetail';
import api from 'api';
import { useState } from 'react';
import { VscHeart, VscHeartFilled } from 'react-icons/vsc';
import { useParams } from 'react-router-dom';

interface OwnProps {
  product: ProductType;
  productCount: number;
  option: OptionListType[];
  selectedValue: number;
}

interface ImageInfoType {
  imageId: number;
  imageOrder: number;
  originalPath: string;
  representative: true;
  thumbnailPath: string;
  title: string;
}

interface ItemList {
  additionalPrice: number;
  count: number;
  defaultPrice: number;
  discountRate: number;
  imageInfo: ImageInfoType;
  itemId: number;
  itemName: string;
  optionId: number;
  optionName: string | null;
}

export default function Button({
  product,
  productCount,
  option,
  selectedValue,
}: OwnProps) {
  const [heart, setHeart] = useState<boolean>(product.like);
  const { id } = useParams();
  const userId: string | null = localStorage.getItem('userId');
  const heartSize = 27;
  const headers = {
    headers: {
      Authorization: localStorage.getItem('authorization'),
    },
  };
  const likeHandler = () => {
    if (userId === null) {
      alert('로그인 후에 좋아해 주세요.');
      return;
    }

    if (userId !== null && id !== undefined) {
      api
        .post(`api/items/${id}/favorite?userId=${userId}`, {}, headers)
        .then((res) => {
          setHeart(!heart);
        })
        .catch((err) => {
          console.log(err);
        });
    }
  };

  const addCartHandler = () => {
    console.log(selectedValue);
    if (userId === null) {
      alert('로그인 후에 이용해주세요.');
      return;
    }

    if (selectedValue === 0) {
      alert('옵션을 선택해주세요.');
      return;
    }

    const targetItemId = Number(id);

    api
      .get(`api/cart/users/${userId}`, headers)
      .then((res) => {
        const itemList = res.data.data.itemList;
        const foundItem = itemList.find(
          (item: ItemList) => item.itemId === targetItemId
        );

        const confirmMessage =
          foundItem !== undefined
            ? '장바구니에 해당 아이템이 이미 존재합니다. 그래도 추가하시겠습니까?'
            : '해당 제품을 장바구니에 추가하시겠습니까?';

        if (window.confirm(confirmMessage)) {
          const requestBody = {
            itemList: [
              {
                optionId: selectedValue,
                itemCount: productCount,
              },
            ],
          };

          const apiEndpoint = `api/cart/users/${userId}`;

          const apiMethod = foundItem !== undefined ? api.patch : api.post;

          apiMethod(apiEndpoint, requestBody, headers)
            .then((response) => {
              alert('해당 제품이 장바구니에 추가되었습니다.');
            })
            .catch((error) => {
              console.log('API 오류:', error);
            });
        }
      })
      .catch((error) => {
        console.log('API 오류:', error);
      });
  };

  return (
    <div className="flex items-center justify-end pt-5">
      <button onClick={likeHandler}>
        {heart ? (
          <VscHeartFilled size={heartSize} />
        ) : (
          <VscHeart size={heartSize} />
        )}
      </button>
      <button
        className="w-48 py-3 ml-2 border rounded-full bg-none text-slate-800 border-slate-800"
        onClick={addCartHandler}
      >
        장바구니 추가
      </button>
      <button className="w-48 py-3 ml-2 rounded-full border-box bg-slate-800 text-slate-200">
        구매하기
      </button>
    </div>
  );
}
