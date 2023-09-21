import type { CategoryContent } from 'Pages/CYW/Products';
import CategoryItem from './CategoryItem';

interface OwnProps {
  categoryData: CategoryContent[] | null;
}

export default function CategoryResult({ categoryData }: OwnProps) {
  return (
    <div className="flex flex-row flex-wrap justify-center pt-20 pb-20 gap-x-8 gap-y-8">
      {categoryData?.map((categoryItem) => (
        <CategoryItem key={categoryItem.itemId} categoryItem={categoryItem} />
      ))}
    </div>
  );
}
