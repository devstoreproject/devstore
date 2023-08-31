import { useNavigate } from 'react-router-dom';
import NewItemInfo from './NewItemInfo';

interface NewItemProps {
  id: number;
  title: string;
  category: any;
  price: string;
  image: string;
  idx: number;
}

export default function NewItem({
  id,
  title,
  category,
  price,
  image,
  idx,
}: NewItemProps) {
  const backgroundImage = {
    backgroundImage: `url('${image}')`,
  };
  const noneBackgroundImage = {
    backgroundImage: `url('https://source.unsplash.com/random')`,
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
      className="relative mx-4 cursor-pointer lg:w-1/4 sm:w-full sm:mb-4 md:w-1/2 md:my-2 md:m-auto md:px-4"
    >
      <div
        className="mb-2 transition-all bg-white bg-cover rounded-tl-none h-60 rounded-xl hover:scale-95"
        style={image !== undefined ? backgroundImage : noneBackgroundImage}
      >
        <div className="flex items-center justify-center w-12 h-12 font-semibold text-white rounded-br-lg bg-box-black">
          {id}
        </div>
      </div>
      <NewItemInfo title={title} category={category} price={price} />
    </button>
  );
}
