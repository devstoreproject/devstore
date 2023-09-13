import { useEffect } from 'react';
interface ProductProp {
  datas: any;
  pathName: string;
  setPrice: React.Dispatch<React.SetStateAction<any>>;
  setEditPrice: React.Dispatch<React.SetStateAction<any>>;
}

export default function PriceInput({
  datas,
  pathName,
  setPrice,
  setEditPrice,
}: ProductProp) {
  const onChangeHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (pathName === 'post') {
      setPrice(parseInt(e.target.value));
    } else {
      setEditPrice(parseInt(e.target.value));
    }
  };
  useEffect(() => {
    if (datas?.itemPrice !== undefined) {
      setEditPrice(datas?.itemPrice);
    }
  }, [datas]);
  return (
    <>
      {pathName === 'post' ? (
        <div className="flex items-center mt-8">
          <label htmlFor="productTitle" className="w-20 text-label-gray">
            금액
          </label>
          <input
            id="productTitle"
            type="text"
            placeholder="숫자만 입력해 주세요 (원 단위)"
            onChange={onChangeHandler}
            className="w-full px-5 py-3 border rounded-3xl border-gray"
          />
        </div>
      ) : (
        <div className="flex items-center mt-8">
          <label htmlFor="productTitle" className="w-20 text-label-gray">
            금액
          </label>
          <input
            id="productTitle"
            type="text"
            placeholder="숫자만 입력해 주세요 (원 단위)"
            onChange={onChangeHandler}
            defaultValue={datas.itemPrice}
            className="w-full px-5 py-3 border rounded-3xl border-gray"
          />
        </div>
      )}
    </>
  );
}
