import { useEffect } from 'react';
interface ProductProp {
  datas: any;
  pathName: string;
  setTitle: React.Dispatch<React.SetStateAction<string>>;
  setEditTitle: React.Dispatch<React.SetStateAction<string>>;
}
export default function TitleInput({
  datas,
  pathName,
  setTitle,
  setEditTitle,
}: ProductProp) {
  const onChangeHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (pathName === 'post') {
      setTitle(e.target.value);
    } else {
      setEditTitle(e.target.value);
    }
  };
  useEffect(() => {
    if (datas?.name !== undefined) {
      setEditTitle(datas?.name);
    }
  }, [datas]);
  return (
    <>
      {pathName === 'post' ? (
        <div className="flex items-center">
          <label htmlFor="productTitle" className="w-20 text-label-gray">
            상품명
          </label>
          <input
            id="productTitle"
            type="text"
            placeholder="상품명을 입력해 주세요"
            onChange={onChangeHandler}
            className="w-full px-5 py-3 border rounded-3xl border-gray"
          />
        </div>
      ) : (
        <div className="flex items-center">
          <label htmlFor="productTitle" className="w-20 text-label-gray">
            상품명
          </label>
          <input
            id="productTitle"
            type="text"
            placeholder="상품명을 입력해 주세요"
            onChange={onChangeHandler}
            defaultValue={datas.name}
            className="w-full px-5 py-3 border rounded-3xl border-gray"
          />
        </div>
      )}
    </>
  );
}
