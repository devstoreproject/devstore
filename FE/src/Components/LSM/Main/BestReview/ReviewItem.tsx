import { useNavigate } from 'react-router-dom';
interface ReviewItemProps {
  image: string;
  comment: string;
  id: number;
  userName: string;
  modifiedAt: string;
}

export default function ReviewItem({
  image,
  comment,
  id,
  userName,
  modifiedAt,
}: ReviewItemProps) {
  const navigate = useNavigate();

  const movePageHandler = () => {
    navigate(`/products/${id}`);
    window.scrollTo(0, 0);
  };

  const date = modifiedAt.slice(0, 10).replaceAll('-', '.');

  return (
    <li className="flex items-center justify-center mr-10 transition-all cursor-pointer hover:shadow-lg rounded-2xl snap-center">
      <button
        type="button"
        onClick={movePageHandler}
        className="text-center bg-white rounded-2xl h-80 w-60"
      >
        <img
          className="object-cover w-full h-48 rounded-tl-2xl rounded-tr-2xl"
          src={image !== null ? image : 'https://source.unsplash.com/random'}
          alt="리뷰 이미지"
        />
        <div className="flex items-center justify-start w-full h-32 px-4">
          <img
            src="https://item.sellpro.co.kr/data/item/125/5565685125_550.jpg"
            alt="상품 이미지"
            className="w-16"
          />
          <div className="flex flex-col items-start justify-center pl-2">
            <p className="text-sm font-semibold text-left line-clamp-1">
              {comment}
            </p>
            <p className="text-xs">{`${userName}님의 리뷰`}</p>
            <p className="text-xs text-tag-gray">{date}</p>
          </div>
        </div>
      </button>
    </li>
  );
}
