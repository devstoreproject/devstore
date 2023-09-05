import Select from './Select';

interface ProductProp {
  // datas: any;
  // path: string;
  category: string;
  setCategory: React.Dispatch<React.SetStateAction<string>>;
  // setEditCategory: React.Dispatch<React.SetStateAction<string>>;
  // editCategory: string;
  // id: string;
}

export default function CategoryInput({ category, setCategory }: ProductProp) {
  return (
    <div className="flex items-center mt-8">
      <label htmlFor="productTitle" className="w-20 text-label-gray">
        카테고리
      </label>
      <Select category={category} setCategory={setCategory} />
    </div>
  );
}
