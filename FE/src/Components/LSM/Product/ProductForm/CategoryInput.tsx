import { useEffect } from 'react';
import Select from './Select';

interface ProductProp {
  datas: any;
  pathName: string;
  setCategory: React.Dispatch<React.SetStateAction<string>>;
  setEditCategory: React.Dispatch<React.SetStateAction<string>>;
  editCategory: string;
}

export default function CategoryInput({
  datas,
  pathName,
  setCategory,
  editCategory,
  setEditCategory,
}: ProductProp) {
  useEffect(() => {
    setEditCategory(datas?.category);
  }, [datas?.category]);

  return (
    <div className="flex items-center mt-8">
      <label htmlFor="productTitle" className="w-20 text-label-gray">
        카테고리
      </label>
      <Select
        pathName={pathName}
        setCategory={setCategory}
        editCategory={editCategory}
        setEditCategory={setEditCategory}
      />
    </div>
  );
}
