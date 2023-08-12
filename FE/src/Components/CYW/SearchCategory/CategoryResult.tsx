import type { CategoryContent } from 'Pages/CYW/SearchCategory';
import CategoryItem from './CategoryItem';

interface OwnProps {
  category: CategoryContent[] | null;
}

export default function CategoryResult({ category }: OwnProps) {
  return (
    <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4 pt-16">
      {category?.map((categoryItem) => (
        <CategoryItem key={categoryItem.itemId} categoryItem={categoryItem} />
      ))}
    </div>
  );
}
