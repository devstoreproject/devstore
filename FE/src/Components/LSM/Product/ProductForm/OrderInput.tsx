import { useState } from 'react';
import CheckBox from './CheckBox';
interface ProductProp {
  deliveryPrice: any;
  setDeliveryPrice: React.Dispatch<React.SetStateAction<any>>;
}

export default function OrderInput({
  deliveryPrice,
  setDeliveryPrice,
}: ProductProp) {
  const [isFree, setIsFree] = useState<boolean>(true);

  const onChangeHandler = (e: any) => {
    const newDeliveredPrice = e.target.value;
    setDeliveryPrice(parseInt(newDeliveredPrice));
    if (newDeliveredPrice === '' || isNaN(parseInt(newDeliveredPrice))) {
      setDeliveryPrice(0);
    }
    if (newDeliveredPrice === '' || parseInt(newDeliveredPrice) === 0) {
      setIsFree(true);
    } else {
      setIsFree(false);
    }
  };

  return (
    <div className="w-full">
      <p className="mb-6 text-subtitle-gray">배송정보</p>
      <div className="flex items-center w-full">
        <div className="flex items-center w-full">
          <label htmlFor="productOrder" className="w-20 text-label-gray">
            배송비
          </label>
          <input
            id="productOrder"
            type="text"
            placeholder="숫자만 입력해 주세요 (원 단위)"
            className="w-full px-5 py-3 border rounded-3xl border-gray"
            onChange={onChangeHandler}
            disabled={isFree}
            value={deliveryPrice}
          />
        </div>
        <CheckBox isFree={isFree} setIsFree={setIsFree} />
      </div>
    </div>
  );
}
