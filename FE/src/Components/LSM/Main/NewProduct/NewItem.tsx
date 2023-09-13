import { useNavigate } from 'react-router-dom';
import NewItemInfo from './NewItemInfo';

interface NewItemProps {
  id: number;
  title: string;
  category: any;
  price: string;
  image: string;
  idx: number;
  currentPage: number;
}

export default function NewItem({
  id,
  title,
  category,
  price,
  image,
  idx,
  currentPage,
}: NewItemProps) {
  const backgroundImage = {
    backgroundImage: `url('${image}')`,
  };
  const noneBackgroundImage = {
    backgroundImage: `url('https://images.unsplash.com/photo-1531297484001-80022131f5a1?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=3440&q=80')`,
  };

  const navigate = useNavigate();

  const onClickHandler = () => {
    navigate(`/products/${id}`);
    window.scrollTo(0, 0);
  };

  return (
    <button
      type="button"
      onClick={onClickHandler}
      className="relative w-1/4 mx-4 cursor-pointer sm:w-full sm:mb-4 md:w-1/2 md:my-2 md:m-auto md:px-4"
    >
      <div
        className="mb-2 transition-all bg-white bg-cover rounded-tl-none h-60 rounded-xl hover:scale-95"
        style={image !== undefined ? backgroundImage : noneBackgroundImage}
      >
        <div className="flex items-center justify-center w-12 h-12 font-semibold text-white rounded-br-lg bg-box-black">
          {idx + 1 + currentPage * 4}
        </div>
      </div>
      <NewItemInfo title={title} category={category} price={price} />
    </button>
  );
}
