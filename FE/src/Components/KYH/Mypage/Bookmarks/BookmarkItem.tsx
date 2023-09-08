import api from 'api';
import type { Bookmark } from 'model/product';
import { useEffect, useState } from 'react';
import { AiOutlineHeart, AiFillHeart } from 'react-icons/ai';
import { useNavigate } from 'react-router-dom';
import addCommasToPrice from 'utils/addCommasToPrice';

export default function BookmarkItem({
  itemId,
  name,
  itemPrice,
  like,
  imageList,
}: Bookmark) {
  const [isBookmark, setIsBookmark] = useState(true);
  const navigate = useNavigate();
  const userId = localStorage.getItem('userId');
  const price = addCommasToPrice(itemPrice);

  useEffect(() => {
    setIsBookmark(like);
  }, [setIsBookmark, like]);

  const favoriteBtnHandler = () => {
    if (userId !== null) {
      api
        .post(`/api/items/${itemId}/favorite?userId=${userId}`)
        .then((res) => {
          setIsBookmark((prev) => !prev);
        })
        .catch((err) => {
          console.log(err);
        });
    }
  };

  return (
    <li className="relative">
      <button
        className="mb-4 mr-4 w-60"
        onClick={() => {
          navigate(`/products/${String(itemId)}`);
        }}
      >
        <img
          className="w-full h-40 mb-2 bg-gray-300 rounded-lg"
          src={imageList[0]?.thumbnailPath}
        />
        <p className="w-56 ml-1 text-sm text-left truncate">{name}</p>
        <span className="flex justify-start ml-1 text-sm text-gray-500">
          {price}원
        </span>
      </button>
      <button
        className="absolute flex items-center justify-center w-6 h-6 bottom-8 right-5 rounded-xl"
        onClick={favoriteBtnHandler}
      >
        {isBookmark ? (
          <AiFillHeart className="text-xl text-neon-green" />
        ) : (
          <AiOutlineHeart className="text-xl" />
        )}
      </button>
    </li>
  );
}
