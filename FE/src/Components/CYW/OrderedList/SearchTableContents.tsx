export default function SearchTableContents() {
  return (
    <div className="flex w-full h-full rounded-lg">
      <label className="flex items-center">
        <input type="checkbox" className="w-5 h-5 ml-6" />
        <span className="ml-2 text-gray-500">주문 완료</span>
      </label>
      <label className="flex items-center">
        <input type="checkbox" className="w-5 h-5 ml-6" />
        <span className="ml-2 text-gray-500">주문 취소</span>
      </label>
      <label className="flex items-center">
        <input type="checkbox" className="w-5 h-5 ml-6" />
        <span className="ml-2 text-gray-500">결제 진행중</span>
      </label>
      <label className="flex items-center">
        <input type="checkbox" className="w-5 h-5 ml-6" />
        <span className="ml-2 text-gray-500">결제 완료</span>
      </label>
      <label className="flex items-center">
        <input type="checkbox" className="w-5 h-5 ml-6" />
        <span className="ml-2 text-gray-500">결제 취소</span>
      </label>
      <label className="flex items-center">
        <input type="checkbox" className="w-5 h-5 ml-6" />
        <span className="ml-2 text-gray-500">배송중</span>
      </label>
      <label className="flex items-center">
        <input type="checkbox" className="w-5 h-5 ml-6" />
        <span className="ml-2 text-gray-500">배송 완료</span>
      </label>
      <label className="flex items-center">
        <input type="checkbox" className="w-5 h-5 ml-6" />
        <span className="ml-2 text-gray-500">환불 진행중</span>
      </label>
      <label className="flex items-center">
        <input type="checkbox" className="w-5 h-5 ml-6" />
        <span className="ml-2 text-gray-500">환불 완료</span>
      </label>
    </div>
  );
}
