import { useNavigate } from 'react-router-dom';
import TagList from '../BestProduct/TagList';

interface ThemeItemProps {
  title: string;
  category: string;
  description: string;
  image: string;
}

export default function ThemeItem({
  title,
  category,
  description,
  image,
}: ThemeItemProps) {
  const backgroundImage = {
    backgroundImage: `url('${image}')`,
  };
  const noneBackgroundImage = {
    backgroundImage: `url('https://source.unsplash.com/random')`,
  };

  const navigate = useNavigate();

  const onClickHandler = () => {
    navigate('/search');
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
