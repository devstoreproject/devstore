import Button from './Button';
import ThemeItem from './ThemeItem';

interface ThemeItemListProps {
  themeData: any;
}

export default function ThemeItemList({ themeData }: ThemeItemListProps) {
  return (
    <div className="relative w-full m-auto">
      <ul className="flex items-center justify-between w-full md:flex-col sm:flex-col">
        {themeData.map((data: any, idx: any) => (
          <ThemeItem
            key={data.itemId}
            category={data.category}
            title={data.name}
            description={data.description}
            image={data.imageList[idx]?.thumbnailPath}
          />
        ))}
      </ul>
      <Button />
    </div>
  );
}
