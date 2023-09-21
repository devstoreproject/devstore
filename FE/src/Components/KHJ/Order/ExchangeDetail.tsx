function ExchangeDetail() {
  return (
    <section className="flex mx-5 py-7 border-b border-gray-300">
      <h2 className="w-36">교환 내역</h2>
      <table className="text-left">
        <tbody>
          <tr className="h-10 align-top">
            <th className="font-normal w-28">교환사유</th>
            <td>상품이 잘못왔습니다.(오배송)</td>
          </tr>
          <tr className="h-10 align-top">
            <th className="font-normal">교환 배송 방법</th>
            <td>쇼핑몰에서 상품을 가져가주세요.</td>
          </tr>
          <tr className="h-10 align-top">
            <th className="font-normal">배송현황</th>
            <td>배송중</td>
          </tr>
          <tr className="h-10 align-top">
            <th className="font-normal">결제</th>
            <td>카드결제</td>
          </tr>
        </tbody>
      </table>
    </section>
  );
}

export default ExchangeDetail;
