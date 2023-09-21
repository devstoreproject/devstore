import { useState, useEffect } from 'react';
import CheckBox from './CheckBox';
interface ProductProp {
  datas: any;
  pathName: string;
  deliveryPrice: any;
  setDeliveryPrice: React.Dispatch<React.SetStateAction<any>>;
  setEditDeliveryPrice: React.Dispatch<React.SetStateAction<any>>;
}

export default function OrderInput({
  datas,
  pathName,
  deliveryPrice,
  setDeliveryPrice,
  setEditDeliveryPrice,
}: ProductProp) {
  const [isFree, setIsFree] = useState<boolean>(false);

  const onChangeHandler = (e: any) => {
    const newDeliveredPrice = e.target.value;
    if (pathName === 'post') {
      setDeliveryPrice(parseInt(newDeliveredPrice));
      if (newDeliveredPrice === '' || isNaN(parseInt(newDeliveredPrice))) {
        setDeliveryPrice(0);
      }
      if (newDeliveredPrice === '' || parseInt(newDeliveredPrice) === 0) {
        setIsFree(true);
      } else {
        setIsFree(false);
      }
    } else {
      setEditDeliveryPrice(parseInt(newDeliveredPrice));
      if (newDeliveredPrice === '' || isNaN(parseInt(newDeliveredPrice))) {
        setEditDeliveryPrice(0);
      }
      if (newDeliveredPrice === '' || parseInt(newDeliveredPrice) === 0) {
        setIsFree(true);
      } else {
        setIsFree(false);
      }
    }
  };

  useEffect(() => {
    if (datas?.deliveryPrice !== undefined) {
      setEditDeliveryPrice(datas?.deliveryPrice);
    }
  }, [datas]);

  return (
    <div className="w-full">
      <p className="mb-6 text-subtitle-gray">배송정보</p>
      <>
        {pathName === 'post' ? (
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
        ) : (
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
                defaultValue={datas.deliveryPrice}
              />
            </div>
            <CheckBox isFree={isFree} setIsFree={setIsFree} />
          </div>
        )}
      </>
    </div>
  );
}
