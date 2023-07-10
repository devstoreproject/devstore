export default function PurchaseItem() {
  return (
    <tr>
      <td className="flex items-center">
        <div className="rounded-xl w-36 h-36 bg-white"></div>
        <div className="text-subtitle-gray ml-5 text-left">
          <p className="text-black">알파스캔</p>
          <span className="mr-4">색상 : 화이트</span>
          <span>너비 : 24인치</span>
        </div>
      </td>
      <td>주문수량 : 1</td>
      <td>1,400</td>
      <td>18,000</td>
      <td>398,000</td>
    </tr>
  );
}
