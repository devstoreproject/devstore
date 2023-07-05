import CheckBox from './CheckBox';

export default function OrderInput() {
  return (
    <div>
      <p className="mb-6 text-subtitle-gray">배송정보</p>
      <div className="flex justify-between">
        <div className="flex items-center w-full">
          <label htmlFor="productOrder" className="w-20 text-label-gray">
            배송비
          </label>
          <input
            id="productOrder"
            type="text"
            placeholder="숫자만 입력해 주세요"
            disabled
            className="w-full px-5 py-3 border rounded-3xl border-gray"
          />
        </div>
        <CheckBox />
      </div>
    </div>
  );
}
