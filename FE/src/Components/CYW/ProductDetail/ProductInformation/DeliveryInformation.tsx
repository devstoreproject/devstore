export default function DeliveryInformation() {
  return (
    <div className="pt-2">
      <div className="flex">
        <p className="text-slate-600">배송 정보</p>
        <p className="pl-4 mr-auto">무료 배송</p>
        <p className="pr-4 text-slate-600">평균 배송 소요 시간</p>
        <p>2~3일</p>
      </div>
      <div className="flex pt-2">
        <p className="pr-12 text-slate-600">적립</p>
        <p>구매 시 4,700원 적립</p>
      </div>
    </div>
  );
}
