export default function OrderStatus() {
  return (
    <div className="flex w-full h-24">
      <div className="flex flex-col items-center w-1/5 h-full mr-2 bg-white rounded-lg shadow-signBox">
        <span className="mt-3 text-gray-500">입금대기</span>
        <span className="mt-2 text-3xl">102</span>
      </div>
      <div className="flex flex-col items-center w-1/5 h-full mr-2 bg-white rounded-lg shadow-signBox">
        <span className="mt-3 text-gray-500">결재완료</span>
        <span className="mt-2 text-3xl">99</span>
      </div>
      <div className="flex flex-col items-center w-1/5 h-full mr-2 bg-white rounded-lg shadow-signBox">
        <span className="mt-3 text-gray-500">배송중</span>
        <span className="mt-2 text-3xl">1102</span>
      </div>
      <div className="flex flex-col items-center w-1/5 h-full mr-2 bg-white rounded-lg shadow-signBox">
        <span className="mt-3 text-gray-500">교환요청</span>
        <span className="mt-2 text-3xl">12</span>
      </div>
      <div className="flex flex-col items-center w-1/5 h-full bg-white rounded-lg shadow-signBox">
        <span className="mt-3 text-gray-500">환불요청</span>
        <span className="mt-2 text-3xl">8</span>
      </div>
    </div>
  );
}
