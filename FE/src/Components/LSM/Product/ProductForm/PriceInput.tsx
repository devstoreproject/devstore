interface ProductProp {
  // datas: any;
  // path: string;
  setPrice: React.Dispatch<React.SetStateAction<any>>;
  // setEditTitle: React.Dispatch<React.SetStateAction<string>>;
}

export default function PriceInput({ setPrice }: ProductProp) {
  const onChangeHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPrice(parseInt(e.target.value));
  };
  return (
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
  );
}
