import CheckBox from './CheckBox';

export default function OrderInput() {
  return (
    <div className="w-full">
      <p className="mb-6 text-subtitle-gray">배송정보</p>
      <div className="flex items-center w-full">
        <div className="flex items-center lg:w-full">
          <label htmlFor="productOrder" className="w-20 text-label-gray">
            배송비
          </label>
          <input
            id="productOrder"
            type="text"
            placeholder="숫자만 입력해 주세요"
            disabled
            className="px-5 py-3 border lg:w-full rounded-3xl border-gray"
          />
        </div>
        <CheckBox />
      </div>
    </div>
  );
}
