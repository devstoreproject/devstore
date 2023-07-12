import TagList from '../BestProduct/TagList';

interface ThemeItemProps {
  title: string;
  tags: string[];
  paragraph: string;
}

export default function ThemeItem({ title, tags, paragraph }: ThemeItemProps) {
  return (
    <li className="relative flex items-center mb-4 mr-2 cursor-pointer md:w-full sm:w-full lg:w-1/2">
      <div className="w-40 h-40 mr-8 transition-all bg-white bg-cover rounded-xl hover:scale-105 bg-theme-item-image"></div>
      <div className="flex flex-col flex-wrap w-80 md:w-full">
        <h3 className="text-lg font-semibold">{title}</h3>
        <p className="py-3 text-xs whitespace-normal text-subtitle-gray">
          {paragraph}
        </p>
        <ul className="flex items-center">
          {tags.map((tag, idx) => (
            <TagList key={idx} tag={tag} />
          ))}
        </ul>
      </div>
    </li>
  );
}
