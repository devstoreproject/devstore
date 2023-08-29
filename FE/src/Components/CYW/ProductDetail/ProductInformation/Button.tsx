import api from 'api';
import { useState } from 'react';
import { VscHeart, VscHeartFilled } from 'react-icons/vsc';
import { useParams } from 'react-router-dom';

export default function Button() {
  const [heart, setHeart] = useState(false);
  const userId: string | null = localStorage.getItem('userId');
  const heartSize = 27;
  const { id } = useParams();

  const likeHandler = () => {
    if (userId === null) {
      alert('로그인 후에 좋아해 주세요.');
      return;
    }

    if (userId !== null && id !== undefined) {
      api
        .post(
          `api/items/${id}/favorite?userId=${userId}`,
          {},
          {
            headers: {
              Authorization: localStorage.getItem('authorization'),
            },
          }
        )
        .then((res) => {
          console.log(res);
          setHeart(!heart);
        })
        .catch((err) => {
          console.log(err);
        });
    }
  };

  return (
    <div className="flex pt-5 items-center justify-end">
      <button onClick={likeHandler}>
        {heart ? (
          <VscHeartFilled size={heartSize} />
        ) : (
          <VscHeart size={heartSize} />
        )}
      </button>
      <button className="border-box outline rounded-full py-3 w-48 mr-4 ml-4">
        장바구니 추가
      </button>
      <button className="border-box rounded-full bg-slate-800 text-slate-200 py-3 w-48">
        구매하기
      </button>
    </div>
  );
}
