interface ProductProp {
  // datas: any;
  // path: string;
  setTitle: React.Dispatch<React.SetStateAction<string>>;
  // setEditTitle: React.Dispatch<React.SetStateAction<string>>;
}
export default function TitleInput({ setTitle }: ProductProp) {
  const onChangeHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    setTitle(e.target.value);
  };
  return (
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
  );
}
