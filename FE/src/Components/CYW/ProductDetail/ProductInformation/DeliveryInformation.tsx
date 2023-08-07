interface DeliveryInformationProps {
  delivery: number;
  price: number;
}

export default function DeliveryInformation({
  delivery,
  price,
}: DeliveryInformationProps) {
  return (
    <div className="pt-2">
      <div className="flex">
        <p className="text-slate-600">배송 비용</p>
        <p className="pl-4 mr-auto">
          {delivery === 0 ? '무료 배송' : `${delivery}원`}
        </p>
        <p className="pr-4 text-slate-600">평균 배송 소요 시간</p>
        <p>2~3일</p>
      </div>
      <div className="flex pt-2">
        <p className="pr-12 text-slate-600">적립</p>
        <p>{`구매 시 ${price * 0.9 * 0.01}원 적립`}</p>
      </div>
    </div>
  );
}
