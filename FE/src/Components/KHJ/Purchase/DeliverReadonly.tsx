import type { OrderAddress } from '../Type/OrderItemTypes';

interface addressProps {
  isShippingFormWrite: OrderAddress;
}

const DeliveryReadonly = ({ isShippingFormWrite }: addressProps) => {
  return (
    <section className="mx-5 py-7 flex border-b border-gray-300">
      <div className="w-36">
        <h2>배송지 정보</h2>
      </div>
      <div className="flex">
        <div className="w-32">
          <p className="h-14 flex items-center">받는 이</p>
          <p className="h-14 flex items-center mt-3">휴대전화</p>
          <p className="h-14 flex items-center mt-3">주소</p>
          <p className="h-14 flex items-center mt-3"></p>
        </div>
        <div>
          <div className="flex items-center">
            <p className="h-14 border border-gray-300 rounded-full px-10 flex items-center w-40">
              {isShippingFormWrite.recipient}
            </p>
          </div>
          <div className="flex items-center mt-3">
            <p className="h-14 border border-gray-300 rounded-full px-10 flex items-center w-64">
              {isShippingFormWrite.phone}
            </p>
          </div>
          <div className="mt-3">
            <p className="h-14 border border-gray-300 rounded-full px-10 flex items-center w-40">
              {isShippingFormWrite.zipCode}
            </p>
            <div className="mt-3 flex gap-2">
              <p className="h-14 border border-gray-300 rounded-full px-10 flex items-center w-64">
                {isShippingFormWrite.addressSimple}
              </p>
              <p className="h-14 border border-gray-300 rounded-full px-10 flex items-center w-64">
                {isShippingFormWrite.addressDetail}
              </p>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};

export default DeliveryReadonly;
