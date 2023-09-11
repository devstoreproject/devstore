import type { ShippingList } from '../Type/ShippingType';
import DeliverForm from './DeliverForm';

interface ShipProps {
  isShippingFormWrite: ShippingList;
  setIsShippingFormWrite: React.Dispatch<React.SetStateAction<ShippingList>>;
  isMe: boolean;
  setIsMe: React.Dispatch<React.SetStateAction<boolean>>;
}

const Delivery = ({
  isShippingFormWrite,
  setIsShippingFormWrite,
  isMe,
  setIsMe,
}: ShipProps) => {
  return (
    <section className="mx-5 py-7 flex border-b border-gray-300">
      <div className="w-36">
        <h2>배송지 정보 입력</h2>
        <p>*은 필수 입력입니다.</p>
      </div>
      <form className="flex">
        <div className="w-32">
          <p className="h-14 flex items-center">받는 이*</p>
          <p className="h-14 flex items-center mt-3">휴대전화*</p>
          <p className="h-14 flex items-center mt-3">주소*</p>
          <p className="h-14 flex items-center mt-3"></p>
          {/* <p className="h-14 flex items-center mt-3">배송요청사항</p> */}
        </div>
        <DeliverForm
          isShippingFormWrite={isShippingFormWrite}
          setIsShippingFormWrite={setIsShippingFormWrite}
          isMe={isMe}
          setIsMe={setIsMe}
        />
      </form>
    </section>
  );
};

export default Delivery;
