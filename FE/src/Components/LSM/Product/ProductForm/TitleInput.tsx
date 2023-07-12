export default function TitleInput() {
  return (
    <div className="flex items-center">
      <label htmlFor="productTitle" className="w-20 text-label-gray">
        상품명
      </label>
      <input
        id="productTitle"
        type="text"
        placeholder="상품명을 입력해 주세요"
        className="px-5 py-3 border lg:w-full rounded-3xl border-gray"
      />
    </div>
  );
}
