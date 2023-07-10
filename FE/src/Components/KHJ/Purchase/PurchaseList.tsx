import PurchaseItem from './PurchaseItem';

export default function PurchaseList() {
  return (
    <section className="mx-5 border-b border-gray-300 pb-7">
      <table className="w-full text-center">
        <tr className="rounded-t-xl shadow-border h-14">
          <th>상품상세</th>
          <th>수량</th>
          <th>적립</th>
          <th>할인</th>
          <th>결제금액</th>
        </tr>
        <PurchaseItem />
        <PurchaseItem />
        <PurchaseItem />
      </table>
    </section>
  );
}
