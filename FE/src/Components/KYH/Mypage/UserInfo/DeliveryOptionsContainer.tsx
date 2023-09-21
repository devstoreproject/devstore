export default function DeliveryOptionsContainer() {
  return (
    <div className="flex items-center mb-4">
      <span className="w-32 text-gray-500">배송요청사항</span>
      <select className="h-10 pl-4 text-sm border border-gray-300 w-96 rounded-3xl">
        <option value="">선택 안함</option>
        <option value="">배송 전, 연락주세요</option>
        <option value="">부재 시, 휴대폰으로 연락주세요</option>
        <option value="">부재 시, 경비실에 맡겨주세요</option>
        <option value="">부재 시, 문앞에 놓고 연락주세요</option>
      </select>
    </div>
  );
}
