import { useNavigate } from 'react-router-dom';
import TagList from '../BestProduct/TagList';

interface ThemeItemProps {
  title: string;
  category: string;
  description: string;
  image: string;
  id: number;
}

export default function ThemeItem({
  title,
  category,
  description,
  image,
  id,
}: ThemeItemProps) {
  const backgroundImage = {
    backgroundImage: `url('${image}')`,
  };
  const noneBackgroundImage = {
    backgroundImage: `url('https://images.unsplash.com/photo-1531297484001-80022131f5a1?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=3440&q=80')`,
  };

  const navigate = useNavigate();

  const onClickHandler = () => {
    navigate(`/search/${category}/${id}`);
    window.scrollTo(0, 0);
  };
  return (
    <li className="relative flex items-center mb-4 mr-2 md:w-full sm:w-full lg:w-1/2">
      <button
        type="button"
        onClick={onClickHandler}
        className="w-40 h-40 mr-8 transition-all bg-white bg-cover rounded-xl hover:scale-105"
        style={image !== undefined ? backgroundImage : noneBackgroundImage}
      ></button>
      <div className="flex flex-col flex-wrap w-80 md:w-full">
        <h3 className="text-lg font-semibold">{title}</h3>
        <p className="py-3 text-xs whitespace-normal text-subtitle-gray">
          {description}
        </p>
        <ul className="flex items-center">
          {[category].map((item, idx) => (
            <TagList key={idx} item={item} />
          ))}
        </ul>
      </div>
    </li>
  );
}
